package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartService {
    Optional<ShoppingCart> getCartByUserId(int userId);
    Optional<Item> getItemById(int userId, int itemId);
    void createCart(int userId);
    void addItemToCart(int userId, int productId);
    void deleteItemFromCart(int userId, int itemId);
    void updateItemQuantity(int userId, int itemId, int quantity);
    void clearCart(int userId);
}
