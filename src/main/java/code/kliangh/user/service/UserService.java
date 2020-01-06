package code.kliangh.user.service;

import code.kliangh.user.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> findAllUsers(Pageable pageable);

    User findUser(Example<User> userExample);

    User findUserByUid(String uid);

    User addUser(User newUser);

    User updateUser(User user) throws Exception;

    void deleteUserByUid(String uid);
}
