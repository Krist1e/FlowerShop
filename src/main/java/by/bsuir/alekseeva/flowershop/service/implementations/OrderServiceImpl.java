package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.dao.OrderDAO;
import by.bsuir.alekseeva.flowershop.dao.UserDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserDAO userDAO;
    private final OrderDAO orderDAO;

    @Override
    public void placeOrder(int userId, String address, String phone, String comments, ShoppingCart cart) throws ServiceException {
        Optional<User> user;
        try {
            user = userDAO.getUserById(userId);
        } catch (DAOException e) {
            log.error("Error while getting user by id", e);
            throw new ServiceException(e);
        }
        if (user.isEmpty()) {
            throw new ServiceException("User not found");
        }

        Order order = Order.builder()
                .user(user.get())
                .address(address)
                .phone(phone)
                .comments(comments)
                .status(OrderStatus.PAID)
                .date(LocalDateTime.now())
                .orderItems(cart.getCartItems())
                .totalPrice(cart.getTotalPrice())
                .build();

        try {
            orderDAO.save(order);
        } catch (DAOException e) {
            log.error("Error while creating order", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> getOrderById(int id) throws ServiceException {
        try {
            return orderDAO.getOrderById(id);
        } catch (DAOException e) {
            log.error("Error while getting order by id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrders() throws ServiceException {
        try {
            return orderDAO.getOrders();
        } catch (DAOException e) {
            log.error("Error while getting orders", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize) throws ServiceException {
        try {
            return orderDAO.getOrders(pageNumber, pageSize);
        } catch (DAOException e) {
            log.error("Error while getting orders", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws ServiceException {
        try {
            return orderDAO.getOrdersByUserId(userId);
        } catch (DAOException e) {
            log.error("Error while getting orders by user id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) throws ServiceException {
        try {
            return orderDAO.getOrdersByUserId(userId, pageNumber, pageSize);
        } catch (DAOException e) {
            log.error("Error while getting orders by user id", e);
            throw new ServiceException(e);
        }
    }
}
