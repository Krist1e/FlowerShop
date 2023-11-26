package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(int id) throws ServiceException;
    Optional<Product> getProductByName(String name) throws ServiceException;
    List<Product> getInStockProducts() throws ServiceException;
    Page<Product> getInStockProducts(int pageNumber, int pageSize) throws ServiceException;
    void addProduct(String name, String description, float price, float discount, String imagePath) throws ServiceException;
    void deleteProduct(int id) throws ServiceException;
    void updateProduct(int id, String name, String description, float price, float discount, String imagePath) throws ServiceException;
}
