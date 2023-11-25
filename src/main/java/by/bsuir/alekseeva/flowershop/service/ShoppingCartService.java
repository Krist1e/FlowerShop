package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartService {
    Optional<ShoppingCart> getCartById(int id);
    void createCartItem(int cartId, int productId, int quantity);
    void deleteItemFromCart(int cartId, int itemId);
    void updateItemInCart(int cartId, int itemId, int quantity);
    void clearCart(int id);

}
