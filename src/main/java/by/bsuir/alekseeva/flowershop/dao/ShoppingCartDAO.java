package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartDAO {
    Optional<ShoppingCart> getCartByUserId(int userId) throws DAOException;
    List<ShoppingCart> getCartsByProductId(int productId) throws DAOException;
    void save(ShoppingCart shoppingCart) throws DAOException;
    void update(ShoppingCart shoppingCart) throws DAOException;
}
