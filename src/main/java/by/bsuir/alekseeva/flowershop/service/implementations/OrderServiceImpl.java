package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final List<Order> orders = new ArrayList<>();

    public OrderServiceImpl() {
        List<Item> orderItems = new ArrayList<>();
        orderItems.add(new Item(1, new Product(1, "Rose", "Red rose", "rose.jpg", 10, 0.0F, 1), 5, 50));
        orderItems.add(new Item(2, new Product(2, "Tulip", "Red tulip", "tulip.jpg", 5, 0.1F, 1), 9, 45));

        orders.add(Order.builder()
                .id(1)
                .user(User.builder()
                        .id(1)
                        .username("user")
                        .password("user")
                        .email("user@gmail.com")
                        .role(Role.USER)
                        .build())
                .orderItems(orderItems)
                .totalPrice(95)
                .status(OrderStatus.PAID)
                .date(LocalDateTime.now())
                .address("Minsk")
                .build());
    }

    @Override
    public void createOrderItem(int orderId, int itemId, int quantity) {
        Item item = new Item();
        Order order = getOrderById(orderId).get();
        item.setId(order.getOrderItems().size() + 1);
        item.setProduct(order.getOrderItems().get(itemId).getProduct());
        item.setQuantity(quantity);
        item.setPrice(item.getProduct().getPrice() * quantity);
        order.getOrderItems().add(item);
        order.setTotalPrice(order.getTotalPrice() + item.getPrice());
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    @Override
    public List<Item> getOrderItems(int orderId) {
        return getOrderById(orderId).get().getOrderItems();
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public void deleteOrder(int id) {
        orders.removeIf(order -> order.getId() == id);
    }

    @Override
    public void changeOrderStatus(int id, OrderStatus status) {
        getOrderById(id).get().setStatus(status);
    }

    @Override
    public void setOrderAddress(int id, String address) {
        getOrderById(id).get().setAddress(address);
    }

    @Override
    public float getTotalPrice(int id) {
        return getOrderById(id).get().getOrderItems().stream()
                .map(Item::getPrice)
                .reduce(0.0F, Float::sum);
    }
}
