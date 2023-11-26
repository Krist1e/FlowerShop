package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    Optional<Coupon> getCouponById(int id) throws ServiceException;
    Optional<Coupon> getCouponByCode(int code) throws ServiceException;
    void createCoupon(String couponName, int code, float discount) throws ServiceException;
    void deleteCoupon(int id) throws ServiceException;
    List<Coupon> getCoupons() throws ServiceException;
    Page<Coupon> getCoupons(int pageNumber, int pageSize) throws ServiceException;
}
