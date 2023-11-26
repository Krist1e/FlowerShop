package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void createOrder(int userId, List<Item> items, String address, String phone, String comments) {
        Order order = new Order();
        order.setId(orders.size() + 1);
        order.setUser(userService.getUserById(userId).get());
        order.setOrderItems(new ArrayList<>(items));
        order.setTotalPrice(items.stream().map(Item::getPrice).reduce(0.0F, Float::sum));
        order.setStatus(OrderStatus.PAID);
        order.setDate(LocalDateTime.now());
        order.setAddress(address);
        order.setPhone(phone);
        order.setComments(comments);
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
