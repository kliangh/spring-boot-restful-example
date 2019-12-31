package code.kliangh.graphql.resolver;

import code.kliangh.user.entity.User;
import code.kliangh.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QueryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Query query;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllUsers() {
        when(userRepository.findAll()).thenReturn(getAllUsers());

        List<User> result = query.findAllUsers();
        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    private List<User> getAllUsers() {
        User testUser1 = new User();
        testUser1.setUid(UUID.randomUUID().toString());

        User testUser2 = new User();
        testUser2.setUid(UUID.randomUUID().toString());

        return Arrays.asList(testUser1, testUser2);
    }
}