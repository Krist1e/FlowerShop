package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.*;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ProductService productService;
    private final List<ShoppingCart> carts = new ArrayList<>();

    public ShoppingCartServiceImpl(ProductService productService) {
        this.productService = productService;
        List<Item> cartItems = new ArrayList<>();
        cartItems.add(new Item(1, new Product(1, "Rose", "Red rose", "rose.jpg", 10, 0.0F, 1), 5, 50));
        cartItems.add(new Item(2, new Product(2, "Tulip", "Red tulip", "tulip.jpg", 5, 0.1F, 1), 9, 45));

        carts.add(ShoppingCart.builder()
                .id(1)
                .user(User.builder()
                        .id(1)
                        .username("user")
                        .password("user")
                        .email("user@gmail.com")
                        .role(Role.GUEST)
                        .build())
                .cartItems(cartItems)
                .totalPrice(95)
                .build());
    }
    @Override
    public Optional<ShoppingCart> getCartById(int id) {
        return carts.stream()
                .filter(cart -> cart.getId() == id)
                .findFirst();
    }

    @Override
    public void createCartItem(int cartId, int productId, int quantity) {
        Item item = new Item();
        ShoppingCart cart = getCartById(cartId).get();
        item.setId(cart.getCartItems().size() + 1);
        item.setProduct(productService.getProductById(productId).get());
        item.setQuantity(quantity);
        item.setPrice(item.getProduct().getPrice() * quantity);
        cart.getCartItems().add(item);
        cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
    }

    @Override
    public void deleteItemFromCart(int cartId, int itemId) {
        getCartById(cartId).get().getCartItems().removeIf(item -> item.getId() == itemId);
    }

    @Override
    public void updateItemInCart(int cartId, int itemId, int quantity) {
        Item item = getCartById(cartId).get().getCartItems().stream()
                .filter(i -> i.getProduct().getId() == itemId)
                .findFirst().get();
        if (quantity == 0) {
            deleteItemFromCart(cartId, itemId);
        } else if (quantity > 0) {
            item.setQuantity(quantity);
            item.setPrice(item.getProduct().getPrice() * quantity);
        } else {
            throw new IllegalArgumentException("Quantity can't be negative");
        }
    }

    @Override
    public void clearCart(int id) {
        getCartById(id).get().getCartItems().clear();
        getCartById(id).get().setTotalPrice(0);
    }
}
