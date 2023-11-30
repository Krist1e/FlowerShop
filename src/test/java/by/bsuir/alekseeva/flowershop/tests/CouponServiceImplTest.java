package by.bsuir.alekseeva.flowershop.tests;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.implementations.CouponServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CouponServiceImplTest {

    @Mock
    private CouponDAO couponDAO;

    private CouponServiceImpl couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        couponService = new CouponServiceImpl(couponDAO);
    }

    @Test
    void testGetCouponById() throws DAOException, ServiceException {
        int id = 1;
        Coupon expectedCoupon = new Coupon(id, 123, "Coupon 1",  0.1f);
        when(couponDAO.getCouponById(id)).thenReturn(Optional.of(expectedCoupon));

        Optional<Coupon> actualCoupon = couponService.getCouponById(id);
        assertTrue(actualCoupon.isPresent());
        assertEquals(expectedCoupon, actualCoupon.get());
    }

    @Test
    void testGetCouponByCode() throws DAOException, ServiceException {
        int code = 123;
        Coupon expectedCoupon = new Coupon(1, code, "Coupon 1", 0.1f);
        when(couponDAO.getCouponByCode(code)).thenReturn(Optional.of(expectedCoupon));

        Optional<Coupon> actualCoupon = couponService.getCouponByCode(code);
        assertTrue(actualCoupon.isPresent());
        assertEquals(expectedCoupon, actualCoupon.get());
    }

    @Test
    void testGetCoupons() throws DAOException, ServiceException {
        List<Coupon> expectedCoupons = new ArrayList<>();
        expectedCoupons.add(new Coupon(1, 123, "Coupon 1", 0.1f));
        expectedCoupons.add(new Coupon(2, 456, "Coupon 2", 0.2f));
        when(couponDAO.getCoupons()).thenReturn(expectedCoupons);

        List<Coupon> actualCoupons = couponService.getCoupons();
        assertEquals(expectedCoupons.size(), actualCoupons.size());
        assertEquals(expectedCoupons, actualCoupons);
    }

}
