package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.util.BeanUtils;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Component
public class UserMutation implements GraphQLMutationResolver {

    private UserRepository userRepository;

    @Autowired
    public UserMutation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(String name, String surname, String remark) {
        User user = new User();
        user.setUid(UUID.randomUUID().toString());
        user.setName(name);
        user.setSurname(surname);
        user.setRemark(remark);

        return userRepository.save(user);
    }

    public User updateUser(String uid, String name, String surname, String remark)
            throws InvocationTargetException, IllegalAccessException {
        User updatedUser = new User();
        updatedUser.setUid(uid);
        updatedUser.setName(name);
        updatedUser.setSurname(surname);
        updatedUser.setRemark(remark);

        User originalUser = userRepository.findById(uid).orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyPropertiesWithValue(updatedUser, originalUser);

        return userRepository.saveAndFlush(originalUser);
    }

    public Boolean deleteUser(String uid) {
        userRepository.deleteById(uid);
        userRepository.flush();

        return !userRepository.existsById(uid);
    }
}
