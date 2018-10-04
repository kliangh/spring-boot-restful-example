package code.kliangh.user.service;

import code.kliangh.user.entity.User;
import org.springframework.data.domain.Example;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUser(Example<User> userExample);

    User findUserByUid(String uid);

    void addUser(User newUser);

    void updateUser(User user) throws Exception;

    void deleteUserByUid(String uid);
}
