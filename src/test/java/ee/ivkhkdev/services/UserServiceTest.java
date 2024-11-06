package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private List<User> users;

    @Mock
    private AppHelper<User> appHelperUser;

    @Mock
    private FileRepository<User> userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        users = new ArrayList<>();
        userService = new UserService(users, appHelperUser);
        when(appHelperUser.getRepository()).thenReturn(userRepository);
    }

    @Test
    void testAdd() {
        User user = new User();
        when(appHelperUser.create()).thenReturn(user);

        boolean result = userService.add();

        assertTrue(result);
        assertEquals(1, users.size());
        assertEquals(user, users.get(0));
        verify(userRepository, times(1)).save(users);
    }

    @Test
    void testAddFailsWhenAppHelperReturnsNull() {
        when(appHelperUser.create()).thenReturn(null);

        boolean result = userService.add();

        assertFalse(result);
        assertEquals(0, users.size());
        verify(userRepository, never()).save(anyList());
    }

    @Test
    void testEdit() {
        User existingUser = new User("John", "Doe", "12345");
        existingUser.setId(UUID.randomUUID());
        users.add(existingUser);

        User updatedUser = new User("John", "Doe", "54321");
        updatedUser.setId(existingUser.getId());

        boolean result = userService.edit(updatedUser);

        assertTrue(result);
        assertEquals("54321", users.get(0).getPhone());
        verify(userRepository, times(1)).save(users);
    }

    @Test
    void testEditUserNotFound() {
        User user = new User("John", "Doe", "12345");
        user.setId(UUID.randomUUID());

        boolean result = userService.edit(user);

        assertFalse(result);
        verify(userRepository, never()).save(anyList());
    }

    @Test
    void testRemove() {
        User user = new User("John", "Doe", "12345");
        user.setId(UUID.randomUUID());
        users.add(user);

        boolean result = userService.remove(user);

        assertTrue(result);
        assertEquals(0, users.size());
        verify(userRepository, times(1)).save(users);
    }

    @Test
    void testRemoveUserNotFound() {
        User user = new User("John", "Doe", "12345");
        user.setId(UUID.randomUUID());

        boolean result = userService.remove(user);

        assertFalse(result);
        verify(userRepository, never()).save(anyList());
    }

    @Test
    void testPrint() {
        userService.print();
        verify(appHelperUser, times(1)).printList(users);
    }

    @Test
    void testList() {
        List<User> result = userService.list();

        assertEquals(users, result);
    }
}
