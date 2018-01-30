package code.kliangh.user.controller;

import code.kliangh.user.entity.User;
import code.kliangh.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    public void findAllUser() throws Exception {
        User testUser1 = new User();
        testUser1.setUid(UUID.randomUUID().toString());
        testUser1.setSurname("Kenyon");

        User testUser2 = new User();
        testUser2.setUid(UUID.randomUUID().toString());
        testUser2.setSurname("Hou");

        List<User> users = Arrays.asList(testUser1, testUser2);

        when(userService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].surname", is("Kenyon")))
                .andExpect(jsonPath("$[1].surname", is("Hou")))
                .andDo(print());
        verify(userService, times(1)).findAllUsers();
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void findUser() throws Exception {
        User testUser = new User();
        String uid = UUID.randomUUID().toString();
        testUser.setUid(uid);
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        when(userService.findUserByUid(uid)).thenReturn(testUser);
        mockMvc.perform(get("/users/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name", is("Kenyon")))
                .andExpect(jsonPath("$.surname", is("Hou")))
                .andDo(print());
        verify(userService, times(1)).findUserByUid(uid);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void addUser() throws Exception {
        User testUser = new User();
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        doNothing().when(userService).addUser(testUser);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andDo(print());
        verify(userService, times(1)).addUser(testUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateUser() throws Exception {
        User testUser = new User();
        String uid = UUID.randomUUID().toString();
        testUser.setUid(uid);
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        User updatedUser = new User();
        updatedUser.setUid(uid);
        updatedUser.setName("Vargo");

        when(userService.findUserByUid(uid)).thenReturn(testUser);
        mockMvc.perform(put("/users/{uid}", uid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userService, times(1)).updateUser(updatedUser);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void deleteUser() throws Exception {
        User testUser = new User();
        String uid = UUID.randomUUID().toString();
        testUser.setUid(uid);
        testUser.setName("Kenyon");
        testUser.setSurname("Hou");

        doNothing().when(userService).deleteUserByUid(testUser.getUid());
        mockMvc.perform(delete("/users/{uid}", uid))
                .andExpect(status().isOk())
                .andDo(print());
        verify(userService, times(1)).deleteUserByUid(uid);
        verifyNoMoreInteractions(userService);
    }
}