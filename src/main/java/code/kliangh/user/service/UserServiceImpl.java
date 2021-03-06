package code.kliangh.user.service;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String CACHE_KEY = "user";

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(value = CACHE_KEY, key = "#uid")
    @Transactional(readOnly = true)
    public User findUserByUid(String uid) {
        return userRepository.findById(uid).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUser(Example<User> userExample) {
        return userRepository.findOne(userExample).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User addUser(User newUser) {
        newUser.setUid(UUID.randomUUID().toString());

        return userRepository.save(newUser);
    }

    @Override
    @CachePut(value = CACHE_KEY, key = "#updatedUser.uid")
    public User updateUser(User updatedUser) throws Exception {
        //Get original user entity
        User originalUser = findUserByUid(updatedUser.getUid());

        //Copy properties with value from updated user to original user
        BeanUtils.copyPropertiesWithValue(updatedUser, originalUser);

        return userRepository.saveAndFlush(originalUser);
    }

    @Override
    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public void deleteUserByUid(String uid) {
        userRepository.deleteById(uid);
        userRepository.flush();
    }

}
