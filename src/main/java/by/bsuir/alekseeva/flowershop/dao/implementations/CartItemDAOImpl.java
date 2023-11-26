package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.dao.CartItemDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.Optional;

public class CartItemDAOImpl implements CartItemDAO {
    @Override
    public Optional<Item> getItemByItemIdAndCartId(int itemId, int cartId) throws DAOException {
        return Optional.empty();
    }

    @Override
    public void saveItem(Item item) {

    }

    @Override
    public void deleteItem(Item item) {

    }

    @Override
    public void updateItem(Item item) {

    }
}
