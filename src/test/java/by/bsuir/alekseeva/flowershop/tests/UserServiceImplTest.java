package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import by.bsuir.alekseeva.flowershop.service.implementations.ProductServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.ShoppingCartServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductServiceImpl();
        ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(productService);
        userService = new UserServiceImpl(shoppingCartService);
    }

    @Test
    void getUserByUsername() {
        String username = "admin";
        Optional<User> user = userService.getUserByUsername(username);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(username, user.get().getUsername());
    }

    @Test
    void getUserById() {
        int id = 1;
        Optional<User> user = userService.getUserById(id);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(id, user.get().getId());
    }

    @Test
    void getUsers() {
        assertNotNull(userService.getUsers());
    }

    @Test
    void addUser() {
        String username = "test";
        String password = "test";
        String email = "test@gmail.com";
        userService.addUser(username, password, email);
        Optional<User> user = userService.getUserByUsername(username);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertEquals(username, user.get().getUsername());
        assertEquals(email, user.get().getEmail());
    }

    @Test
    void banUser() {
        int id = 1;
        userService.banUser(id);
        Optional<User> user = userService.getUserById(id);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertSame(user.get().getRole(), Role.BANNED_USER);
    }

    @Test
    void unbanUser() {
        int id = 1;
        userService.unbanUser(id);
        Optional<User> user = userService.getUserById(id);
        assertNotNull(user);
        assertTrue(user.isPresent());
        assertSame(user.get().getRole(), Role.USER);
    }
}