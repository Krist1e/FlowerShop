package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.dao.implementations.*;
import lombok.Getter;

@Getter
public class DAOFactory {
    @Getter
    private static final DAOFactory instance;

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final CouponDAO couponDAO;
    private final ShoppingCartDAO shoppingCartDAO;

    static {
        instance = new DAOFactory();
    }

    private DAOFactory() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        userDAO = new UserDAOImpl(connectionPool);
        productDAO = new ProductDAOImpl(connectionPool);
        orderDAO = new OrderDAOImpl(connectionPool);
        couponDAO = new CouponDAOImpl(connectionPool);
        shoppingCartDAO = new ShoppingCartDAOImpl(connectionPool);
    }
}
