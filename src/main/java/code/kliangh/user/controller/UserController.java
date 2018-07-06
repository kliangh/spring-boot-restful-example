package code.kliangh.user.controller;

import code.kliangh.user.entity.User;
import code.kliangh.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //Get all user from userRepository
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> findAllUser() {
        return userService.findAllUsers();
    }

    //Get an user by UID
    @GetMapping("/{uid}")
    @ResponseStatus(value = HttpStatus.OK)
    public User getUser(@PathVariable String uid) {
        return userService.findUserByUid(uid);
    }

    //Add an user to userRepository
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addUser(@RequestBody User newUser) {
        userService.addUser(newUser);
    }


    //Update specific field of an user, name or surname
    @PutMapping("/{uid}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateUser(@RequestBody User user) throws Exception {

        userService.updateUser(user);
    }

    //Delete an user by UID
    @DeleteMapping("/{uid}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable String uid) {

        userService.deleteUserByUid(uid);
    }

}
