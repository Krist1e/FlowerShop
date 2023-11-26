package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.Optional;

public interface CartItemDAO {
    Optional<Item> getItemByItemIdAndCartId(int itemId, int cartId) throws DAOException;
    void saveItem(Item item);
    void deleteItem(Item item);
    void updateItem(Item item);
}
