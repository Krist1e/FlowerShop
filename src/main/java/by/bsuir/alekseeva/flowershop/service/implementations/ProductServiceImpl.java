package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductServiceImpl() {
        for (int i = 0; i < 100; i++) {
            addProduct("Product " + i, "Description " + i, i * 100, i / 10f, "images/product.png");
        }
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
    public List<Product> getInStockProducts() {
        return products;
    }

    @Override
    public Page<Product> getInStockProducts(int pageNumber, int pageSize) {
        return products.stream().collect(Page.toPage(pageNumber, pageSize));
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
