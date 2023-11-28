package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ItemMapper {
    private ItemMapper() {
    }

    public static Item toEntity(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .id(resultSet.getInt("item_id"))
                .quantity(resultSet.getInt("item_quantity"))
                .price(resultSet.getFloat("item_price"))
                .product(Product.builder()
                        .id(resultSet.getInt("product_id"))
                        .name(resultSet.getString("product_name"))
                        .description(resultSet.getString("product_description"))
                        .price(resultSet.getFloat("product_price"))
                        .discount(resultSet.getFloat("product_discount"))
                        .imagePath(resultSet.getString("product_imagePath"))
                        .build())
                .build();
    }

    public static List<Item> toEntityList(ResultSet resultSet) throws SQLException {
        List<Item> cartItems = new ArrayList<>();
        if (resultSet.getInt("item_id") == 0) {
            return cartItems;
        }
        do {
            cartItems.add(Item.builder()
                    .id(resultSet.getInt("item_id"))
                    .quantity(resultSet.getInt("item_quantity"))
                    .price(resultSet.getFloat("item_price"))
                    .product(Product.builder()
                            .id(resultSet.getInt("product_id"))
                            .name(resultSet.getString("product_name"))
                            .description(resultSet.getString("product_description"))
                            .price(resultSet.getFloat("product_price"))
                            .discount(resultSet.getFloat("product_discount"))
                            .imagePath(resultSet.getString("product_imagePath"))
                            .build())
                    .build());
        } while (resultSet.next());
        return cartItems;
    }
}
