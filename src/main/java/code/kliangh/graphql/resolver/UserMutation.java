package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.user.service.UserService;
import code.kliangh.util.BeanUtils;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Component
public class UserMutation implements GraphQLMutationResolver {

    private UserService userService;

    @Autowired
    public UserMutation(UserService userService) {
        this.userService = userService;
    }

    public User addUser(String name, String surname, String remark) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setRemark(remark);

        return userService.addUser(newUser);
    }

    public User updateUser(String uid, String name, String surname, String remark)
            throws Exception {
        User updatingRequest = new User();
        updatingRequest.setUid(uid);
        updatingRequest.setName(name);
        updatingRequest.setSurname(surname);
        updatingRequest.setRemark(remark);

        return userService.updateUser(updatingRequest);
    }

    public void deleteUser(String uid) {
        userService.deleteUserByUid(uid);
    }
}
