package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.service.*;
import by.bsuir.alekseeva.flowershop.service.implementations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationServiceImplTest {
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        CouponService couponService = new CouponServiceImpl();
        ProductService productService = new ProductServiceImpl();
        ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(productService, couponService);
        UserService userService = new UserServiceImpl(shoppingCartService);
        authenticationService = new AuthenticationServiceImpl(userService);
    }

    @Test
    void registerAndAuthenticate() {
        String email = "admin2@gmail.com";
        String username = "admin2";
        String password = "admin2";
        assertTrue(authenticationService.register(email, username, password).isSuccess());
        assertTrue(authenticationService.authenticate(username, password).isSuccess());
    }
}