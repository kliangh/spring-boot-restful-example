package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import code.kliangh.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserMutationTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserMutation userMutation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUser() {
        User testUser = getTestUser();
        when(userService.addUser(any())).thenReturn(testUser);
        User resultUser = userMutation.addUser(testUser.getName(), testUser.getSurname(),
                                               testUser.getRemark());

        assertEquals(resultUser, testUser);

        verify(userService, times(1)).addUser(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUser() throws Exception {
        User updatedUser = getTestUser();
        updatedUser.setRemark("Ha ha!");

        when(userService.updateUser(any())).thenReturn(updatedUser);
        userMutation.updateUser(updatedUser.getUid(), updatedUser.getName(),
                                updatedUser.getSurname(), "Ha ha!");

        verify(userService, times(1)).updateUser(any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void deleteUser() {
        User testUser = getTestUser();
        doNothing().when(userService).deleteUserByUid(testUser.getUid());

        userMutation.deleteUser(testUser.getUid());

        verify(userService, times(1)).deleteUserByUid(testUser.getUid());
        verifyNoMoreInteractions(userService);
    }

    private User getTestUser() {
        User testUser = new User();
        testUser.setUid(UUID.randomUUID().toString());
        testUser.setName("Foo");
        testUser.setSurname("Bar");
        testUser.setRemark("Dummy!");

        return testUser;
    }
}