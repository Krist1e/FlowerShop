package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void placeOrder(int userId, String address, String phone, String comments, ShoppingCart cart) throws ServiceException;
    Optional<Order> getOrderById(int id) throws ServiceException;
    List<Order> getOrders() throws ServiceException;
    Page<Order> getOrders(int pageNumber, int pageSize) throws ServiceException;
    List<Order> getOrdersByUserId(int userId) throws ServiceException;
    Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) throws ServiceException;
}
