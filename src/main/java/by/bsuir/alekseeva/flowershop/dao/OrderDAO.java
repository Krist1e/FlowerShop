package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    void save(Order order) throws DAOException;
    Optional<Order> getOrderById(int id) throws DAOException;
    List<Order> getOrdersByUserId(int userId) throws DAOException;
    Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) throws DAOException;
    List<Order> getOrders() throws DAOException;

    Page<Order> getOrders(int pageNumber, int pageSize) throws DAOException;
}
