package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import by.bsuir.alekseeva.flowershop.service.implementations.AuthenticationServiceImpl;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationServiceImplTest {

    private AuthenticationService authenticationService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        authenticationService = new AuthenticationServiceImpl(userService);
    }

    @Test
    public void testAuthenticate_Successful() throws ServiceException {
        String username = "testUser";
        String password = "testPassword";
        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        AuthenticationService.AuthenticationResult result = authenticationService.authenticate(username, password);

        assertTrue(result.isSuccess());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testAuthenticate_WrongPassword() throws ServiceException {
        String username = "testUser";
        String password = "testPassword";
        String hashedPassword = Hashing.sha256().hashString("wrongPassword", StandardCharsets.UTF_8).toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        when(userService.getUserByUsername(username)).thenReturn(Optional.of(user));

        AuthenticationService.AuthenticationResult result = authenticationService.authenticate(username, password);

        assertFalse(result.isSuccess());
        assertEquals("wrong_password", result.getMessage());
    }

    @Test
    void testRegister_Success() throws ServiceException {
        String username = "newUser";
        String email = "newUser@example.com";
        String password = "testPassword";

        when(userService.getUserByUsername(username)).thenReturn(Optional.empty());

        assertEquals(authenticationService.register(email, username, password).getMessage(), "success");
    }

    @Test
    void testRegister_UserAlreadyExists() throws ServiceException {
        String username = "existingUser";
        String email = "existingUser@example.com";
        String password = "testPassword";

        when(userService.getUserByUsername(username)).thenReturn(Optional.of(new User()));

        assertEquals(authenticationService.register(email, username, password).getMessage(), "user_already_exists");
    }

}
