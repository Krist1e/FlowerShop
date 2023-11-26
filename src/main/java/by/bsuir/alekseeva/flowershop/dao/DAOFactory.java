package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.dao.implementations.*;
import lombok.Getter;

@Getter
public class DAOFactory {
    @Getter
    private final static DAOFactory instance;

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final CouponDAO couponDAO;
    private final ShoppingCartDAO shoppingCartDAO;
    private final CartItemDAO cartItemDAO;
    private final RoleDAO roleDAO;

    static {
        instance = new DAOFactory();
    }

    private DAOFactory() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        roleDAO = new RoleDAOImpl();
        userDAO = new UserDAOImpl(connectionPool, roleDAO);
        productDAO = new ProductDAOImpl();
        orderDAO = new OrderDAOImpl();
        couponDAO = new CouponDAOImpl();
        shoppingCartDAO = new ShoppingCartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
    }
}
