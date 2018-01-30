package code.kliangh.util;

import code.kliangh.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeanUtilsTest {

    private User originalUser;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        BeanUtils beanUtils = new BeanUtils();
        originalUser = new User();
        String uid = UUID.randomUUID().toString();

        originalUser.setUid(uid);
        originalUser.setName("Kenyon");
        originalUser.setSurname("Hou");

        updatedUser = new User();

        updatedUser.setUid(uid);
        updatedUser.setName("Vargo");
    }

    @Test
    void copyPropertiesWithValue() throws Exception {

        BeanUtils.copyPropertiesWithValue(updatedUser, originalUser);

        assertEquals(updatedUser.getName(), originalUser.getName());
    }
}