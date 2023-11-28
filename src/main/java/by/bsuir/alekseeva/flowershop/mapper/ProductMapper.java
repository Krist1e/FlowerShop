package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductMapper {
    private ProductMapper() {
    }

    public static Product toEntity(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getFloat("price"))
                .discount(resultSet.getFloat("discount"))
                .imagePath(resultSet.getString("imagePath"))
                .build();
    }

    public static List<Product> toEntityList(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(toEntity(resultSet));
        }
        return products;
    }
}
