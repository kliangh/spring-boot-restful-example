package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserQueryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQuery userQuery;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllUsers() {
        when(userRepository.findAll()).thenReturn(getAllUsers());

        List<User> result = userQuery.findAllUsers();
        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void user() {
        User testUser = getTestUser();

        when(userRepository.findById(testUser.getUid())).thenReturn(Optional.of(testUser));

        User result = userQuery.user(testUser.getUid());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getSurname(), result.getSurname());

        verify(userRepository, times(1)).findById(testUser.getUid());
        verifyNoMoreInteractions(userRepository);
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
