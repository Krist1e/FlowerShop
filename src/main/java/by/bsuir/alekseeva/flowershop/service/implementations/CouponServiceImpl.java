package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.service.CouponService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouponServiceImpl implements CouponService {
    private final List<Coupon> coupons = new ArrayList<>();

    public CouponServiceImpl() {
        coupons.add(new Coupon(1, 123, "10% DISCOUNT", 0.1f));
        coupons.add(new Coupon(2, 456, "20% DISCOUNT", 0.2f));
        coupons.add(new Coupon(3, 789, "30% DISCOUNT", 0.3f));
    }

    @Override
    public Optional<Coupon> getCouponById(int id) {
        return coupons.stream().filter(coupon -> coupon.getId() == id).findFirst();
    }

    @Override
    public Optional<Coupon> getCouponByCode(int code) {
        return coupons.stream().filter(coupon -> coupon.getCode() == code).findFirst();
    }

    @Override
    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public Page<Coupon> getCoupons(int pageNumber, int pageSize) {
        return coupons.stream().collect(Page.toPage(pageNumber, pageSize));
    }

    @Override
    public void createCoupon(String couponName, int code, float discount) {
        coupons.add(new Coupon(coupons.size() + 1, code, couponName, discount));
    }

    @Override
    public void deleteCoupon(int id) {
        coupons.removeIf(coupon -> coupon.getId() == id);
    }
}
