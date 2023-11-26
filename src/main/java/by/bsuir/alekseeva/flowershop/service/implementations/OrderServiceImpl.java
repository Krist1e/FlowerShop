package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final List<Order> orders = new ArrayList<>();

    public OrderServiceImpl(UserService userService) {
        this.userService = userService;
        User user = userService.getUserById(1).get();
        List<Item> orderItems = new ArrayList<>();
        orderItems.add(new Item(1, new Product(1, "Rose", "Red rose", "rose.jpg", 10, 0.0F), 5, 50));
        orderItems.add(new Item(2, new Product(2, "Tulip", "Red tulip", "tulip.jpg", 5, 0.1F), 9, 45));

        orders.add(Order.builder()
                .id(1)
                .user(user)
                .orderItems(orderItems)
                .totalPrice(95)
                .status(OrderStatus.PAID)
                .date(LocalDateTime.now())
                .address("Minsk")
                .build());
    }

    @Override
    public void createOrder(int userId, List<Item> items, String address) {
        Order order = new Order();
        order.setId(orders.size() + 1);
        order.setUser(userService.getUserById(userId).get());
        order.setOrderItems(items);
        order.setTotalPrice(items.stream().map(Item::getPrice).reduce(0.0F, Float::sum));
        order.setStatus(OrderStatus.PAID);
        order.setDate(LocalDateTime.now());
        order.setAddress(address);
        orders.add(order);
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize) {
        return orders.stream().collect(Page.toPage(pageNumber, pageSize));
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        return orders.stream()
                .filter(order -> order.getUser().getId() == userId)
                .toList();
    }

    @Override
    public Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) {
        return getOrdersByUserId(userId).stream().collect(Page.toPage(pageNumber, pageSize));
    }

    @Override
    public void deleteOrder(int id) {
        orders.removeIf(order -> order.getId() == id);
    }

    @Override
    public void changeOrderStatus(int id, OrderStatus status) {
        getOrderById(id).get().setStatus(status);
    }
}
