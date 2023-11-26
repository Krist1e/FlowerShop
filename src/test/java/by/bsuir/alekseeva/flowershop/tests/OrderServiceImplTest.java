package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.OrderStatus;
import by.bsuir.alekseeva.flowershop.service.*;
import by.bsuir.alekseeva.flowershop.service.implementations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderServiceImplTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        CouponService couponService = new CouponServiceImpl();
        ProductService productService = new ProductServiceImpl();
        ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(productService, couponService);
        UserService userService = new UserServiceImpl(shoppingCartService);
        orderService = new OrderServiceImpl(userService);
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
    void changeOrderStatus() {
        int id = 1;
        OrderStatus status = OrderStatus.DELIVERED;
        orderService.changeOrderStatus(id, status);
        assertEquals(status, orderService.getOrderById(id).get().getStatus());
    }

    @Test
    void getOrdersByUserId() {

    }
}