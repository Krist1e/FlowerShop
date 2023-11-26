package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;

import java.util.Optional;

public interface ShoppingCartService {
    Optional<ShoppingCart> getCartByUserId(int userId) throws ServiceException;
    Optional<Item> getItemById(int userId, int itemId) throws ServiceException;
    void addItemToCart(int userId, int productId) throws ServiceException;
    void deleteItemFromCart(int userId, int itemId) throws ServiceException;
    void updateItemQuantity(int userId, int itemId, int quantity) throws ServiceException;
    void clearCart(int userId) throws ServiceException;
    void applyCoupon(int userId, int couponId) throws ServiceException;
}
