package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.impl.AppHelperUsers;
import ee.ivkhkdev.interfaces.impl.ConsoleInput;
import ee.ivkhkdev.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private ConsoleInput inputMock;
    private AppHelperUsers userProviderMock;
    private User userMock;

    @BeforeEach
    void setUp() {
        this.inputMock = Mockito.mock(ConsoleInput.class);
        this.userProviderMock = Mockito.mock(AppHelperUsers.class);
        this.userMock = Mockito.mock(User.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testUserService() {
        when(userProviderMock.create(inputMock)).thenReturn(userMock);
        UserService userService = new UserService(userProviderMock);
        boolean result = userService.add(inputMock);
        verify(userProviderMock).create(inputMock);
        assertTrue(result);
    }
}