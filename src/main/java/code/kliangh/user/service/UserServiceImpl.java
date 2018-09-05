package code.kliangh.user.service;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger("UserServiceImpl.class");

    private static final String CACHE_KEY = "user";

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = CACHE_KEY, key = "#uid")
    @Transactional(readOnly = true)
    public User findUserByUid(String uid) {
        return userRepository.findOne(uid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        Pageable pageable = new PageRequest(0, 20);

        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public void addUser(User newUser) {
        newUser.setUid(UUID.randomUUID().toString());

        userRepository.save(newUser);
    }

    @Override
    @CachePut(value = CACHE_KEY, key = "#updatedUser.uid")
    public void updateUser(User updatedUser) throws Exception {
        //Get original user entity
        User originalUser = userRepository.findOne(updatedUser.getUid());

        //Copy properties with value from updated user to original user
        BeanUtils.copyPropertiesWithValue(updatedUser, originalUser);

        userRepository.saveAndFlush(originalUser);
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public void deleteUserByUid(String uid) {
        userRepository.delete(uid);
        userRepository.flush();
    }

}
