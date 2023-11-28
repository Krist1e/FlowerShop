package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OrderMapper {
    private OrderMapper() {
    }

    public static Order toEntity(ResultSet resultSet) throws SQLException {
        Order order = toEntityWithoutItems(resultSet);
        order.setOrderItems(ItemMapper.toEntityList(resultSet));
        return order;
    }

    public static List<Order> toEntityList(ResultSet resultSet) throws SQLException {
        Map<Integer, Order> orders = new HashMap<>();
        while (resultSet.next()) {
            int orderId = resultSet.getInt("order_id");
            if (!orders.containsKey(orderId)) {
                orders.put(orderId, toEntityWithoutItems(resultSet));
            }

            Item item = ItemMapper.toEntity(resultSet);
            orders.get(orderId).getOrderItems().add(item);
        }
        return new ArrayList<>(orders.values());
    }

    public static Page<Order> toEntityPage(ResultSet resultSet, int pageNumber, int pageSize, int totalRecords) throws SQLException {
        return Page.<Order>builder()
                .content(toEntityList(resultSet))
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalRecords)
                .totalPages((int) Math.ceil((double) totalRecords / pageSize))
                .build();
    }

    private static Order toEntityWithoutItems(ResultSet resultSet) throws SQLException {
        return Order.builder()
                .id(resultSet.getInt("order_id"))
                .totalPrice(resultSet.getFloat("total_price"))
                .date(resultSet.getTimestamp("order_datetime").toLocalDateTime())
                .address(resultSet.getString("address"))
                .phone(resultSet.getString("telephone"))
                .comments(resultSet.getString("comments"))
                .status(OrderStatus.valueOf(resultSet.getString("status")))
                .coupon(resultSet.getInt("coupon_id") == 0 ? null : Coupon.builder()
                        .id(resultSet.getInt("coupon_id"))
                        .code(resultSet.getInt("coupon_code"))
                        .name(resultSet.getString("coupon_name"))
                        .discount(resultSet.getFloat("coupon_discount"))
                        .build())
                .user(User.builder()
                        .id(resultSet.getInt("user_id"))
                        .username(resultSet.getString("username"))
                        .email(resultSet.getString("email"))
                        .build())
                .orderItems(new ArrayList<>())
                .build();
    }
}
