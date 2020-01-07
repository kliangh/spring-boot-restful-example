package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.user.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {

    private UserService userService;

    @Autowired
    public UserQuery(UserService userService) {
        this.userService = userService;
    }

    public List<User> findAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.findAllUsers(pageable);
    }

    public User user(String uid) {
        return userService.findUserByUid(uid);
    }
}
