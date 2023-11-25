package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.implementations.OrderServiceImpl;
import by.bsuir.alekseeva.flowershop.service.implementations.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    void getProductById() {
        int id = 1;
        assertNotNull(productService.getProductById(id));
    }

    @Test
    void getProducts() {
        assertNotNull(productService.getProducts());
    }

    @Test
    void addProduct() {
        String name = "test";
        String description = "test";
        int price = 1;
        float discount = 0.1F;
        String imagePath = "test";
        productService.addProduct(name, description, price, discount, imagePath);
        assertNotNull(productService.getProductByName(name));
        assertEquals(name, productService.getProductByName(name).get().getName());
        assertEquals(description, productService.getProductByName(name).get().getDescription());
        assertEquals(price, productService.getProductByName(name).get().getPrice());
        assertEquals(discount, productService.getProductByName(name).get().getDiscount());
        assertEquals(imagePath, productService.getProductByName(name).get().getImagePath());
    }

    @Test
    void deleteProduct() {
        int id = 1;
        productService.deleteProduct(id);
        assertEquals(Optional.empty(), productService.getProductById(id));
    }

    @Test
    void updateProduct() {
        int id = 1;
        String name = "test";
        String description = "test";
        int price = 1;
        float discount = 0.1F;
        String imagePath = "test";
        productService.updateProduct(id, name, description, price, discount, imagePath);
        assertEquals(name, productService.getProductById(id).get().getName());
        assertEquals(description, productService.getProductById(id).get().getDescription());
        assertEquals(price, productService.getProductById(id).get().getPrice());
        assertEquals(discount, productService.getProductById(id).get().getDiscount());
        assertEquals(imagePath, productService.getProductById(id).get().getImagePath());
    }
}