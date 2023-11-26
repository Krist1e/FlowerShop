package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> getProductById(int id) throws DAOException;
    Optional<Product> getProductByName(String name) throws DAOException;
    List<Product> getInStockProducts() throws DAOException;
    Page<Product> getInStockProducts(int pageNumber, int pageSize) throws DAOException;
    void save(Product product) throws DAOException;
    void update(Product product) throws DAOException;
    void delete(int id) throws DAOException;
}
