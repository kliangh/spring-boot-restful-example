package code.kliangh.user.service;

import code.kliangh.user.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findUserByUid(String uid);

    void addUser(User newUser);

    void updateUser(User user) throws Exception;

    void deleteUserByUid(String uid);
}
