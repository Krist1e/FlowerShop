package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.beans.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ShoppingCartMapper {
    private ShoppingCartMapper() {
    }

    public static ShoppingCart toEntity(ResultSet resultSet) throws SQLException {
        ShoppingCart cart = toEntityWithoutItems(resultSet);
        cart.setCartItems(ItemMapper.toEntityList(resultSet));
        return cart;
    }

    public static List<ShoppingCart> toEntityList(ResultSet resultSet) throws SQLException {
        Map<Integer, ShoppingCart> carts = new HashMap<>();
        while (resultSet.next()) {
            int cartId = resultSet.getInt("cart_id");
            if (!carts.containsKey(cartId)) {
                carts.put(cartId, toEntityWithoutItems(resultSet));
            }

            Item item = ItemMapper.toEntity(resultSet);
            carts.get(cartId).getCartItems().add(item);
        }
        return new ArrayList<>(carts.values());
    }

    private static ShoppingCart toEntityWithoutItems(ResultSet resultSet) throws SQLException {
        return ShoppingCart.builder()
                .id(resultSet.getInt("cart_id"))
                .user(User.builder()
                        .id(resultSet.getInt("user_id"))
                        .build())
                .totalPrice(resultSet.getFloat("total_price"))
                .coupon(resultSet.getInt("coupon_id") == 0 ? null : Coupon.builder()
                        .id(resultSet.getInt("coupon_id"))
                        .code(resultSet.getInt("coupon_code"))
                        .name(resultSet.getString("coupon_name"))
                        .discount(resultSet.getFloat("coupon_discount"))
                        .build())
                .cartItems(new ArrayList<>())
                .build();
    }
}
