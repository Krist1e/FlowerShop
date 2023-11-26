package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Optional<Coupon> getCouponById(int id);
    Optional<Coupon> getCouponByCode(int code);
    void createCoupon(String couponName, int code, float discount);
    void deleteCoupon(int id);
    List<Coupon> getCoupons();
    Page<Coupon> getCoupons(int pageNumber, int pageSize);
}
