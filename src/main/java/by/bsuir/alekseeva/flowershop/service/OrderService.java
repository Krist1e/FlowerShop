package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    void createOrderItem(int orderId, int itemId, int quantity);
    Optional<Order> getOrderById(int id);
    List<Item> getOrderItems(int orderId);
    List<Order> getOrders();
    void deleteOrder(int id);
    void changeOrderStatus(int id, OrderStatus status);
    void setOrderAddress(int id, String address);
    float getTotalPrice(int id);
}
