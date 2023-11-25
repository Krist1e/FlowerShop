package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.OrderStatus;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.implementations.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void createOrderItem() {
        int orderId = 1;
        int itemId = 1;
        int quantity = 5;
        orderService.createOrderItem(orderId, itemId, quantity);
        assertNotNull(orderService.getOrderById(orderId).get().getOrderItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst());
    }

    @Test
    void getOrderById() {
        int id = 1;
        assertNotNull(orderService.getOrderById(id));
    }

    @Test
    void getOrderItems() {
        int id = 1;
        assertNotNull(orderService.getOrderById(id).get().getOrderItems());
    }

    @Test
    void getOrders() {
        assertNotNull(orderService.getOrders());
    }

    @Test
    void deleteOrder() {
        int id = 1;
        orderService.deleteOrder(id);
        assertEquals(Optional.empty(), orderService.getOrderById(id));
    }

    @Test
    void changeOrderStatus() {
        int id = 1;
        OrderStatus status = OrderStatus.DELIVERED;
        orderService.changeOrderStatus(id, status);
        assertEquals(status, orderService.getOrderById(id).get().getStatus());
    }

    @Test
    void setOrderAddress() {
        int id = 1;
        String address = "Minsk";
        orderService.setOrderAddress(id, address);
        assertEquals(address, orderService.getOrderById(id).get().getAddress());
    }

    @Test
    void getTotalPrice() {
        int id = 1;
        float totalPrice = 95;
        assertEquals(totalPrice, orderService.getOrderById(id).get().getOrderItems().stream()
                .map(item -> item.getProduct().getPrice() * item.getQuantity())
                .reduce(0.0F, Float::sum));
    }
}