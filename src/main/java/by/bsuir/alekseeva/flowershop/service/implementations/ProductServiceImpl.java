package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ShoppingCartDAO shoppingCartDAO;

    @Override
    public Optional<Product> getProductById(int id) throws ServiceException {
        try {
            return productDAO.getProductById(id);
        } catch (DAOException e) {
            log.error("Error while getting product by id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Product> getProductByName(String name) throws ServiceException {
        try {
            return productDAO.getProductByName(name);
        } catch (DAOException e) {
            log.error("Error while getting product by name", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> getInStockProducts() throws ServiceException {
        try {
            return productDAO.getInStockProducts();
        } catch (DAOException e) {
            log.error("Error while getting in stock products", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Product> getInStockProducts(int pageNumber, int pageSize) throws ServiceException {
        try {
            return productDAO.getInStockProducts(pageNumber, pageSize);
        } catch (DAOException e) {
            log.error("Error while getting in stock products", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void addProduct(String name, String description, float price, float discount, String imagePath) throws ServiceException {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .discount(discount)
                .imagePath(imagePath)
                .build();
        try {
            productDAO.save(product);
        } catch (DAOException e) {
            log.error("Error while adding product", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteProduct(int id) throws ServiceException {
        try {
            productDAO.delete(id);
        } catch (DAOException e) {
            log.error("Error while deleting product", e);
            throw new ServiceException(e);
        }
        List<ShoppingCart> carts = getShoppingCartsForUpdate(id);
        deleteFromCarts(id, carts);
    }

    private void deleteFromCarts(int id, List<ShoppingCart> carts) throws ServiceException {
        for (ShoppingCart cart : carts) {
            List<Item> items = new ArrayList<>(cart.getCartItems());
            for (Item item : items) {
                if (item.getProduct().getId() == id) {
                    cart.setTotalPrice(cart.getTotalPrice() - item.getPrice());
                    cart.getCartItems().remove(item);
                }
            }
            try {
                shoppingCartDAO.update(cart);
            } catch (DAOException e) {
                log.error("Error while updating cart", e);
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void updateProduct(int id, String name, String description, float price, float discount, String imagePath) throws ServiceException {
        Product product = Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .discount(discount)
                .imagePath(imagePath)
                .build();
        try {
            productDAO.update(product);
        } catch (DAOException e) {
            log.error("Error while updating product", e);
            throw new ServiceException(e);
        }
        List<ShoppingCart> carts = getShoppingCartsForUpdate(id);
        updateCarts(id, carts);
    }

    private void updateCarts(int id, List<ShoppingCart> carts) throws ServiceException {
        for (ShoppingCart cart : carts) {
            List<Item> items = new ArrayList<>(cart.getCartItems());
            for (Item item : items) {
                if (item.getProduct().getId() == id) {
                    cart.setTotalPrice(cart.getTotalPrice() - item.getPrice());
                    item.setPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
                    cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
                }
            }
            try {
                shoppingCartDAO.update(cart);
            } catch (DAOException e) {
                log.error("Error while updating cart", e);
                throw new ServiceException(e);
            }
        }
    }

    private List<ShoppingCart> getShoppingCartsForUpdate(int id) throws ServiceException {
        List<ShoppingCart> carts;
        try {
            carts = shoppingCartDAO.getCartsByProductId(id);
        } catch (DAOException e) {
            log.error("Error while getting carts by product id", e);
            throw new ServiceException(e);
        }
        return carts;
    }
}
