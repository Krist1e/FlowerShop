package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.implementations.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private CouponDAO couponDAO;

    @Mock
    private ShoppingCartDAO shoppingCartDAO;

    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCartService = new ShoppingCartServiceImpl(productDAO, couponDAO, shoppingCartDAO);
    }

    @Test
    void testGetCartByUserId() throws DAOException, ServiceException {
        int userId = 1;
        ShoppingCart expectedCart = new ShoppingCart();
        User user = new User();
        user.setId(userId);
        expectedCart.setUser(user);

        when(shoppingCartDAO.getCartByUserId(userId)).thenReturn(Optional.of(expectedCart));

        Optional<ShoppingCart> actualCart = shoppingCartService.getCartByUserId(userId);
        assertTrue(actualCart.isPresent());
        assertEquals(expectedCart, actualCart.get());
    }

    @Test
    void testApplyCoupon() throws DAOException, ServiceException {
        int userId = 1;
        int couponId = 10;
        ShoppingCart cart = new ShoppingCart();
        User user = new User();
        user.setId(userId);
        cart.setUser(user);
        Coupon coupon = new Coupon(couponId, 123, "Coupon", 0.5f);
        when(shoppingCartDAO.getCartByUserId(userId)).thenReturn(Optional.of(cart));
        when(couponDAO.getCouponById(couponId)).thenReturn(Optional.of(coupon));

        shoppingCartService.applyCoupon(userId, couponId);

        assertEquals(coupon, cart.getCoupon());
        verify(shoppingCartDAO, times(1)).update(cart);
    }

}