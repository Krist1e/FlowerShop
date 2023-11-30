package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.implementations.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ShoppingCartDAO shoppingCartDAO;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productDAO, shoppingCartDAO);
    }

    @Test
    void testGetProductById() throws DAOException, ServiceException {
        int productId = 1;
        Product expectedProduct = new Product(productId, "Test Product", "Description", null, 0.0f, 0);
        when(productDAO.getProductById(productId)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> actualProduct = productService.getProductById(productId);
        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct, actualProduct.get());
    }

    @Test
    void testGetProductByName() throws DAOException, ServiceException {
        String productName = "Test Product";
        Product expectedProduct = new Product(1, productName, "Description", null, 0.0f, 0.1f);
        when(productDAO.getProductByName(productName)).thenReturn(Optional.of(expectedProduct));

        Optional<Product> actualProduct = productService.getProductByName(productName);
        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct, actualProduct.get());
    }

    @Test
    void testGetInStockProducts() throws DAOException, ServiceException {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1, "Product 1", "Description", null, 0.0f, 0));
        expectedProducts.add(new Product(2, "Product 2", "Description", null, 5.0f, 0));
        when(productDAO.getInStockProducts()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getInStockProducts();
        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts, actualProducts);
    }

   }
