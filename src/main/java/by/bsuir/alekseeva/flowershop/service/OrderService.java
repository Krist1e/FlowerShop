package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.OrderStatus;
import by.bsuir.alekseeva.flowershop.beans.Page;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void createOrder(int userId, List<Item> items, String address);
    Optional<Order> getOrderById(int id);
    List<Order> getOrders();
    Page<Order> getOrders(int pageNumber, int pageSize);
    List<Order> getOrdersByUserId(int userId);
    Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize);
    void deleteOrder(int id);
    void changeOrderStatus(int id, OrderStatus status);
}
