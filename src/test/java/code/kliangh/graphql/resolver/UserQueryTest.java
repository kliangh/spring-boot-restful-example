package code.kliangh.graphql.resolver;

import  code.kliangh.user.entity.User;
import code.kliangh.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserQueryTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserQuery userQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllUsers() {
        when(userService.findAllUsers(any(Pageable.class))).thenReturn(getAllUsers());

        List<User> result = userQuery.findAllUsers(0, 10);
        assertEquals(2, result.size());

        verify(userService, times(1)).findAllUsers(any(Pageable.class));
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void user() {
        User testUser = getTestUser();

        when(userService.findUserByUid(testUser.getUid())).thenReturn(testUser);

        User result = userQuery.user(testUser.getUid());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getSurname(), result.getSurname());

        verify(userService, times(1)).findUserByUid(testUser.getUid());
        verifyNoMoreInteractions(userService);
    }

    private User getTestUser() {
        User testUser = new User();
        testUser.setUid(UUID.randomUUID().toString());
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");
        return testUser;
    }

    private List<User> getAllUsers() {
        User testUser1 = new User();
        testUser1.setUid(UUID.randomUUID().toString());

        User testUser2 = new User();
        testUser2.setUid(UUID.randomUUID().toString());

        return Arrays.asList(testUser1, testUser2);
    }
}
