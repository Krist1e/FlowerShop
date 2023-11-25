package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import by.bsuir.alekseeva.flowershop.service.implementations.AuthenticationServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.OrderServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.UserServiceImpl;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        UserService userService = new UserServiceImpl();
        authenticationService = new AuthenticationServiceImpl(userService);
    }

    @Test
    void registerAndAuthenticate() {
        String email = "admin2@gmail.com";
        String username = "admin2";
        String password = "admin2";
        String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        assertTrue(authenticationService.register(email, username, password).isSuccess());
        assertTrue(authenticationService.authenticate(username, password).isSuccess());
    }
}