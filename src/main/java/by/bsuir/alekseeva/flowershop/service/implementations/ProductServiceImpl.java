package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductServiceImpl() {
        products.add(Product.builder()
                .id(1)
                .name("Rose")
                .price(10)
                .build());
        products.add(Product.builder()
                .id(2)
                .name("Tulip")
                .price(5)
                .discount(0.1F)
                .build());
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return products.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public void addProduct(String name, String description, int price, float discount, String imagePath) {
        products.add(Product.builder()
                .id(products.size() + 1)
                .name(name)
                .description(description)
                .price(price)
                .discount(discount)
                .imagePath(imagePath)
                .build());
    }

    @Override
    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }

    @Override
    public void updateProduct(int id, String name, String description, int price, float discount, String imagePath) {
        getProductById(id).ifPresent(product -> {
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setDiscount(discount);
            product.setImagePath(imagePath);
        });
    }
}
