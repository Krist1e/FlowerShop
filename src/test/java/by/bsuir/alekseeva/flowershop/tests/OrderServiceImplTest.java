package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.dao.OrderDAO;
import by.bsuir.alekseeva.flowershop.dao.UserDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.implementations.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private OrderDAO orderDAO;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(userDAO, orderDAO);
    }

    @Test
    void testGetOrderById() throws DAOException, ServiceException {
        int orderId = 1;
        Product product = new Product();
        product.setPrice(10);
        Item item = new Item();
        item.setProduct(product);
        List<Item> items = new ArrayList<>(List.of(item));
        Order expectedOrder = new Order(orderId, new User(), items, 500, OrderStatus.PAID, LocalDateTime.now(), "Address", null, null, null);

        when(orderDAO.getOrderById(orderId)).thenReturn(Optional.of(expectedOrder));

        Optional<Order> actualOrder = orderService.getOrderById(orderId);
        assertTrue(actualOrder.isPresent());
        assertEquals(expectedOrder, actualOrder.get());
    }
  }
