package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface CouponDAO {
    Optional<Coupon> getCouponById(int id) throws DAOException;
    Optional<Coupon> getCouponByCode(int code) throws DAOException;
    List<Coupon> getCoupons() throws DAOException;
    Page<Coupon> getCoupons(int pageNumber, int pageSize) throws DAOException;
    void save(Coupon coupon) throws DAOException;
    void delete(int id) throws DAOException;
}
