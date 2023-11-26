package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.dao.DAOFactory;
import by.bsuir.alekseeva.flowershop.service.implementations.*;
import lombok.Getter;

@Getter
public class ServiceFactory {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final AuthenticationService authenticationService;
    private final ShoppingCartService cartService;
    private final CouponService couponService;

    private ServiceFactory() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        couponService = new CouponServiceImpl(daoFactory.getCouponDAO());
        productService = new ProductServiceImpl(daoFactory.getProductDAO());
        cartService = new ShoppingCartServiceImpl(daoFactory.getProductDAO(), daoFactory.getCouponDAO(), daoFactory.getShoppingCartDAO());
        userService = new UserServiceImpl(daoFactory.getShoppingCartDAO(), daoFactory.getUserDAO());
        orderService = new OrderServiceImpl(daoFactory.getUserDAO(), daoFactory.getOrderDAO());
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

