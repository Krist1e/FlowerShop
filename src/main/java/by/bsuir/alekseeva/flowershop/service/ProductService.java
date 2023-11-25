package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(int id);
    Optional<Product> getProductByName(String name);
    List<Product> getProducts();
    void addProduct(String name, String description, int price, float discount, String imagePath);
    void deleteProduct(int id);
    void updateProduct(int id, String name, String description, int price, float discount, String imagePath);
}
