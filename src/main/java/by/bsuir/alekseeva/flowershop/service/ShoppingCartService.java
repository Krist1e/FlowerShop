package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartService {
    Optional<ShoppingCart> getCartByUserId(int userId);
    void addItemToCart(int userId, int productId);
    void deleteItemFromCart(int userId, int itemId);
    void updateItemInCart(int userId, int itemId, int quantity);
    void clearCart(int userId);
}
