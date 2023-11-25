package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.service.CouponService;
import by.bsuir.alekseeva.flowershop.service.implementations.CouponServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CouponServiceImplTest {
    private CouponService couponService;
    @BeforeEach
    void setUp() {
        couponService = new CouponServiceImpl();
    }

    @Test
    void getCouponById() {
        int id = 1;
        couponService.getCouponById(id);
        assertNotNull(couponService.getCouponById(id));
    }

    @Test
    void getCouponByCode() {
        int code = 123;
        couponService.getCouponByCode(code);
        assertNotNull(couponService.getCouponByCode(code));
    }

    @Test
    void getCoupons() {
        assertNotNull(couponService.getCoupons());
    }

    @Test
    void createCoupon() {
        String couponName = "10% DISCOUNT";
        int code = 123;
        float discount = 0.1F;
        couponService.createCoupon(couponName, code, discount);
        assertNotNull(couponService.getCouponByCode(code));
        assertEquals(couponName, couponService.getCouponByCode(code).get().getName());
        assertEquals(code, couponService.getCouponByCode(code).get().getCode());
        assertEquals(discount, couponService.getCouponByCode(code).get().getDiscount());
    }

    @Test
    void deleteCoupon() {
        int id = 1;
        couponService.deleteCoupon(id);
        assertEquals(Optional.empty(), couponService.getCouponById(id));
    }
}