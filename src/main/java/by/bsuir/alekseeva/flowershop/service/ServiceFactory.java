package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.service.implementations.*;
import lombok.Getter;

@Getter
public class ServiceFactory {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final AuthenticationService authenticationService;
    private final ShoppingCartService cartService;

    private ServiceFactory() {
        productService = new ProductServiceImpl();
        cartService = new ShoppingCartServiceImpl(productService);
        userService = new UserServiceImpl(cartService);
        orderService = new OrderServiceImpl(userService);
        authenticationService = new AuthenticationServiceImpl(userService);
    }

    private static final ServiceFactory serviceFactory;

    static {
        serviceFactory = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }
}

