package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductService productService;
    private final Map<Integer, ShoppingCart> carts = new HashMap<>();

    public ShoppingCartServiceImpl(ProductService productService) {
        this.productService = productService;
        List<Item> cartItems = new ArrayList<>();
        cartItems.add(new Item(1, new Product(1, "Rose", "Red rose", "rose.jpg", 10, 0.0F), 5, 50));
        cartItems.add(new Item(2, new Product(2, "Tulip", "Red tulip", "tulip.jpg", 5, 0.1F), 9, 45));

        carts.put(1, ShoppingCart.builder()
                .cartItems(cartItems)
                .totalPrice(95)
                .build());
    }

    @Override
    public Optional<ShoppingCart> getCartByUserId(int userId) {
        return Optional.ofNullable(carts.get(userId));
    }

    @Override
    public Optional<Item> getItemById(int userId, int itemId) {
        return getCartByUserId(userId).get().getCartItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst();
    }

    @Override
    public void createCart(int userId) {
        carts.put(userId, ShoppingCart.builder()
                .cartItems(new ArrayList<>())
                .totalPrice(0)
                .build());
    }

    @Override
    public void addItemToCart(int userId, int productId) {
        ShoppingCart cart = getShoppingCart(userId);
        Optional<Item> itemOptional = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId() == productId)
                .findFirst();
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setQuantity(item.getQuantity() + 1);
            item.setPrice(item.getProduct().getPrice() * item.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() + item.getProduct().getPrice());
        } else {
            Optional<Product> product = productService.getProductById(productId);
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }
            Item item = Item.builder()
                    .id(cart.getCartItems().size() + 1)
                    .product(product.get())
                    .quantity(1)
                    .price(product.get().getPrice())
                    .build();
            cart.getCartItems().add(item);
            cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
        }

        log.info("Item {} added to cart {}", productId, userId);
    }


    @Override
    public void deleteItemFromCart(int userId, int itemId) {
        ShoppingCart cart = getShoppingCart(userId);
        Item item = cart.getCartItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst().get();
        cart.getCartItems().remove(item);
        cart.setTotalPrice(cart.getTotalPrice() - item.getPrice());
    }

    @Override
    public void updateItemQuantity(int userId, int itemId, int quantity) {
        ShoppingCart cart = getShoppingCart(userId);
        Item item = cart.getCartItems().stream()
                .filter(i -> i.getId() == itemId)
                .findFirst().get();
        cart.setTotalPrice(cart.getTotalPrice() - item.getPrice());
        if (quantity == 0) {
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setPrice(item.getProduct().getPrice() * item.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
        }
    }

    @Override
    public void clearCart(int userId) {
        ShoppingCart cart = getShoppingCart(userId);
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
    }

    private ShoppingCart getShoppingCart(int userId) {
        Optional<ShoppingCart> cartOptional = getCartByUserId(userId);
        if (cartOptional.isEmpty()) {
            throw new IllegalArgumentException("Cart not found");
        }
        return cartOptional.get();
    }
}
