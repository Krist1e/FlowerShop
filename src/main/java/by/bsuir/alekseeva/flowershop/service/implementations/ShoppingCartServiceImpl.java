package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductDAO productDAO;
    private final CouponDAO couponDAO;
    private final ShoppingCartDAO shoppingCartDAO;

    @Override
    public Optional<ShoppingCart> getCartByUserId(int userId) throws ServiceException {
        try {
            return shoppingCartDAO.getCartByUserId(userId);
        } catch (DAOException e) {
            log.error("Error while getting cart by user id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Item> getItemById(int userId, int itemId) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        return cart.getCartItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst();
    }

    @Override
    public void addItemToCart(int userId, int productId) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        Optional<Item> itemOptional = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId() == productId)
                .findFirst();
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setQuantity(item.getQuantity() + 1);
            item.setPrice(item.getPrice() + item.getProduct().getDiscountedPrice());
            cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getDiscountedPrice());
        } else {
            createItem(productId, cart);
        }
        try {
            shoppingCartDAO.update(cart);
        } catch (DAOException e) {
            log.error("Error while updating cart", e);
            throw new ServiceException(e);
        }
        log.debug("Item {} added to cart {}", productId, userId);
    }

    private void createItem(int productId, ShoppingCart cart) throws ServiceException {
        Optional<Product> product;
        try {
            product = productDAO.getProductById(productId);
        } catch (DAOException e) {
            log.error("Error while getting product by id", e);
            throw new ServiceException(e);
        }
        if (product.isEmpty()) {
            log.error("Product not found");
            throw new ServiceException("Product not found");
        }
        Item item = Item.builder()
                .product(product.get())
                .quantity(1)
                .build();
        item.setPrice(item.getProduct().getDiscountedPrice());
        cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getDiscountedPrice());
        cart.getCartItems().add(item);
    }


    @Override
    public void deleteItemFromCart(int userId, int itemId) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        Item item = getItem(cart, itemId);
        cart.setTotalPrice(cart.getTotalPrice() - item.getPrice());
        cart.getCartItems().remove(item);
        try {
            shoppingCartDAO.update(cart);
        } catch (DAOException e) {
            log.error("Error while updating cart", e);
            throw new ServiceException(e);
        }
        log.debug("Item {} deleted from cart {}", itemId, userId);
    }

    @Override
    public void updateItemQuantity(int userId, int itemId, int quantity) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        if (quantity <= 0) {
            deleteItemFromCart(userId, itemId);
            return;
        }
        Item item = getItem(cart, itemId);
        item.setQuantity(quantity);
        float price = item.getProduct().getDiscountedPrice() * quantity;
        cart.setTotalPrice(cart.getTotalPrice() - item.getPrice() + price);
        item.setPrice(price);
        try {
            shoppingCartDAO.update(cart);
        } catch (DAOException e) {
            log.error("Error while updating cart", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void clearCart(int userId) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cart.setCoupon(null);
        try {
            shoppingCartDAO.update(cart);
        } catch (DAOException e) {
            log.error("Error while updating cart", e);
            throw new ServiceException(e);
        }
        log.info("Cart {} cleared", userId);
    }

    @Override
    public void applyCoupon(int userId, int couponId) throws ServiceException {
        ShoppingCart cart = getShoppingCart(userId);
        Optional<Coupon> couponOptional;
        try {
            couponOptional = couponDAO.getCouponById(couponId);
        } catch (DAOException e) {
            log.error("Error while getting coupon by id", e);
            throw new ServiceException(e);
        }
        if (couponOptional.isEmpty()) {
            throw new ServiceException("Coupon not found");
        }
        Coupon coupon = couponOptional.get();
        cart.setCoupon(coupon);
        try {
            shoppingCartDAO.update(cart);
        } catch (DAOException e) {
            log.error("Error while updating cart", e);
            throw new ServiceException(e);
        }
    }

    private Item getItem(ShoppingCart cart, int itemId) throws ServiceException {
        Optional<Item> itemOptional = cart.getCartItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst();
        if (itemOptional.isEmpty()) {
            log.error("Item not found");
            throw new ServiceException("Item not found");
        }
        return itemOptional.get();
    }

    private ShoppingCart getShoppingCart(int userId) throws ServiceException {
        Optional<ShoppingCart> cartOptional = getCartByUserId(userId);
        if (cartOptional.isEmpty()) {
            log.error("Cart not found");
            throw new ServiceException("Cart not found");
        }
        return cartOptional.get();
    }
}
