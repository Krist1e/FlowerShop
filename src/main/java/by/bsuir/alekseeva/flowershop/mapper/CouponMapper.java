package by.bsuir.alekseeva.flowershop.mapper;

import by.bsuir.alekseeva.flowershop.beans.Coupon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CouponMapper {
    private CouponMapper() {
    }


    public static Coupon toEntity(ResultSet resultSet) throws SQLException {
        return Coupon.builder()
                .id(resultSet.getInt("id"))
                .code(resultSet.getInt("code"))
                .name(resultSet.getString("name"))
                .discount(resultSet.getFloat("discount"))
                .build();
    }

    public static List<Coupon> toEntityList(ResultSet resultSet) throws SQLException {
        List<Coupon> coupons = new ArrayList<>();
        while (resultSet.next()) {
            coupons.add(toEntity(resultSet));
        }
        return coupons;
    }
}
