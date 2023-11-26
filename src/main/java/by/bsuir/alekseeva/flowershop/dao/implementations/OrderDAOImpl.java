package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.OrderDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public void save(Order order) throws DAOException {

    }

    @Override
    public Optional<Order> getOrderById(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) throws DAOException {
        return null;
    }

    @Override
    public Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) throws DAOException {
        return null;
    }

    @Override
    public List<Order> getOrders() throws DAOException {
        return null;
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize) throws DAOException {
        return null;
    }
}
