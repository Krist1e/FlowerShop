package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.Optional;

public class ShoppingCartDAOImpl implements ShoppingCartDAO {
    @Override
    public Optional<ShoppingCart> getCartByUserId(int userId) throws DAOException {
        return Optional.empty();
    }

    @Override
    public void save(ShoppingCart shoppingCart) throws DAOException {

    }

    @Override
    public void update(ShoppingCart shoppingCart) throws DAOException {

    }
}
