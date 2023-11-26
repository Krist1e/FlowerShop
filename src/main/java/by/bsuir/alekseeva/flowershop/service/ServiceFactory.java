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
        userService = new UserServiceImpl();
        productService = new ProductServiceImpl();
        orderService = new OrderServiceImpl(userService);
        authenticationService = new AuthenticationServiceImpl(userService);
        cartService = new ShoppingCartServiceImpl(productService);
    }

    private static final ServiceFactory serviceFactory;

    static {
        serviceFactory = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }
}

