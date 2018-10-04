package code.kliangh.user.service;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserByUid() throws Exception {
        User testUser = new User();

        String uid = UUID.randomUUID().toString();
        testUser.setUid(uid);
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        when(userRepository.findOne(uid)).thenReturn(testUser);

        User resultUser = userServiceImpl.findUserByUid(uid);
        assertEquals(uid, resultUser.getUid());
        assertEquals("Kenyon", resultUser.getName());
        assertEquals("Hou", resultUser.getSurname());

        verify(userRepository, times(1)).findOne(uid);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void findUser() {
        User testUser = new User();
        testUser.setName("Kenyon");
        Example<User> userExample = Example.of(testUser);

        User expectedUser = new User();
        expectedUser.setUid(UUID.randomUUID().toString());
        expectedUser.setName("Kenyon");
        expectedUser.setSurname("Hou");

        when(userRepository.findOne(Example.of(testUser))).thenReturn(expectedUser);

        User result = userServiceImpl.findUser(userExample);
        assertEquals(result.getName(), testUser.getName());
        assertEquals(result.getSurname(), "Hou");

        verify(userRepository, times(1)).findOne(userExample);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void findAllUsers() throws Exception {
        User testUser1 = new User();
        testUser1.setUid(UUID.randomUUID().toString());

        User testUser2 = new User();
        testUser2.setUid(UUID.randomUUID().toString());

        List<User> users = Arrays.asList(testUser1, testUser2);
        Pageable pageable = new PageRequest(0, 20);

        Page<User> pageableUsers = new PageImpl<>(users);

        when(userRepository.findAll(pageable)).thenReturn(pageableUsers);

        List<User> resultUser = userServiceImpl.findAllUsers();
        assertEquals(2, resultUser.size());

        verify(userRepository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void addUser() throws Exception {
        User testUser = new User();
        testUser.setUid(UUID.randomUUID().toString());
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        when(userRepository.save(testUser)).thenReturn(testUser);

        userServiceImpl.addUser(testUser);

        verify(userRepository, times(1)).save(testUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void updateUser() throws Exception {
        User originalUser = new User();
        String uid = UUID.randomUUID().toString();
        originalUser.setUid(uid);
        originalUser.setName("Kenyon");
        originalUser.setSurname("Hou");

        User updatedUser = new User();
        updatedUser.setUid(uid);
        updatedUser.setName("Vargo");

        when(userRepository.save(originalUser)).thenReturn(originalUser);
        when(userRepository.findOne(uid)).thenReturn(originalUser);

        userServiceImpl.updateUser(updatedUser);

        User resultUser = userServiceImpl.findUserByUid(uid);
        assertEquals("Vargo", resultUser.getName());

        verify(userRepository, times(2)).findOne(originalUser.getUid());
        verify(userRepository, times(1)).saveAndFlush(originalUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void deleteUserByUid() throws Exception {

        String uid = UUID.randomUUID().toString();

        doNothing().when(userRepository).delete(uid);

        userServiceImpl.deleteUserByUid(uid);

        verify(userRepository, times(1)).delete(uid);
        verify(userRepository, times(1)).flush();
        verifyNoMoreInteractions(userRepository);
    }
}