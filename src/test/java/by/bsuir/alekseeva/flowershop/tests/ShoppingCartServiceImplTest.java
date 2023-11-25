package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.service.implementations.OrderServiceImpl;
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
        ProductService productService = new ProductServiceImpl();
        cartService = new ShoppingCartServiceImpl(productService);
    }

    @Test
    void getCartById() {
        int id = 1;
        assertNotNull(cartService.getCartById(id));
    }

    @Test
    void createCartItem() {
        cartService.createCartItem(1, 1, 5);
        assertNotNull(cartService.getCartById(1));
    }

    @Test
    void deleteItemFromCart() {
        int cartId = 1;
        int itemId = 1;
        cartService.deleteItemFromCart(cartId, itemId);
        ShoppingCart cart = cartService.getCartById(1).get();
        assertEquals(Optional.empty(), cart.getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst());
    }

    @Test
    void updateItemInCart() {
        int cartId = 1;
        int itemId = 1;
        int quantity = 10;
        cartService.updateItemInCart(cartId, itemId, quantity);
        Item cartItem = cartService.getCartById(1).get().getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst().get();
        cartItem.setQuantity(quantity);
        ShoppingCart cart = cartService.getCartById(1).get();
        assertEquals(quantity, cart.getCartItems().stream()
                .filter(item -> item.getId() == itemId)
                .findFirst().get().getQuantity());
    }

    @Test
    void clearCart() {
        int id = 1;
        cartService.clearCart(id);
        ShoppingCart cart = cartService.getCartById(id).get();
        assertEquals(0, cart.getCartItems().size());
    }
}