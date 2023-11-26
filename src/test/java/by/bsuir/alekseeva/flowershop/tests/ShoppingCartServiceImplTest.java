package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.service.CouponService;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.service.implementations.CouponServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.ProductServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingCartServiceImplTest {
    private ShoppingCartService cartService;

    @BeforeEach
    void setUp() {
        CouponService couponService = new CouponServiceImpl();
        ProductService productService = new ProductServiceImpl();
        cartService = new ShoppingCartServiceImpl(productService, couponService);
    }

    @Test
    void getCartById() {
        int id = 1;
        assertNotNull(cartService.getCartByUserId(id));
    }

    @Test
    void deleteItemFromCart() {
        int cartId = 1;
        int itemId = 1;
        cartService.deleteItemFromCart(cartId, itemId);
        ShoppingCart cart = cartService.getCartByUserId(1).get();
        assertEquals(Optional.empty(), cart.getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst());
    }

    @Test
    void updateItemInCart() {
        int cartId = 1;
        int itemId = 1;
        int quantity = 10;
        cartService.updateItemQuantity(cartId, itemId, quantity);
        Item cartItem = cartService.getCartByUserId(1).get().getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst().get();
        cartItem.setQuantity(quantity);
        ShoppingCart cart = cartService.getCartByUserId(1).get();
        assertEquals(quantity, cart.getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst().get().getQuantity());
    }

    @Test
    void clearCart() {
        int id = 1;
        cartService.clearCart(id);
        ShoppingCart cart = cartService.getCartByUserId(id).get();
        assertEquals(0, cart.getCartItems().size());
    }
}