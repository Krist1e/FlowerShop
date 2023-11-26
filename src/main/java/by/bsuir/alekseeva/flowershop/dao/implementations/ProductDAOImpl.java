package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public Optional<Product> getProductById(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public Optional<Product> getProductByName(String name) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Product> getInStockProducts() throws DAOException {
        return null;
    }

    @Override
    public Page<Product> getInStockProducts(int pageNumber, int pageSize) throws DAOException {
        return null;
    }

    @Override
    public void save(Product product) throws DAOException {

    }

    @Override
    public void update(Product product) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }
}
