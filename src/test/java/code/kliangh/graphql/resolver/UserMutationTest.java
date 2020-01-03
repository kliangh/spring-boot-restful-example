package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserMutationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserMutation userMutation;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addUser() {
        User testUser = getTestUser();
        when(userRepository.save(any())).thenReturn(testUser);
        User resultUser = userMutation.addUser(testUser.getName(), testUser.getSurname(),
                                               testUser.getRemark());

        assertEquals(resultUser, testUser);

        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void updateUser() throws InvocationTargetException, IllegalAccessException {
        User testUser = getTestUser();

        when(userRepository.findById(testUser.getUid())).thenReturn(Optional.of(testUser));
        when(userRepository.saveAndFlush(testUser)).thenReturn(testUser);

        User result = userMutation.updateUser(testUser.getUid(), testUser.getName(),
                                              testUser.getSurname(), "Ha ha!");
        assertEquals(testUser, result);
        assertEquals("Ha ha!", testUser.getRemark());

        verify(userRepository, times(1)).findById(testUser.getUid());
        verify(userRepository, times(1)).saveAndFlush(testUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void deleteUser() {
        User testUser = getTestUser();
        doNothing().when(userRepository).deleteById(testUser.getUid());

        userMutation.deleteUser(testUser.getUid());

        verify(userRepository, times(1)).deleteById(testUser.getUid());
        verify(userRepository, times(1)).flush();
        verify(userRepository, times(1)).existsById(testUser.getUid());
        verifyNoMoreInteractions(userRepository);
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