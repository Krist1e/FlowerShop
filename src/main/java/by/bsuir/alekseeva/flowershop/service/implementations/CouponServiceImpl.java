package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponDAO couponDAO;

    @Override
    public Optional<Coupon> getCouponById(int id) throws ServiceException {
        try {
            return couponDAO.getCouponById(id);
        } catch (DAOException e) {
            log.error("Error while getting coupon by id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Coupon> getCouponByCode(int code) throws ServiceException {
        try {
            return couponDAO.getCouponByCode(code);
        } catch (DAOException e) {
            log.error("Error while getting coupon by code", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Coupon> getCoupons() throws ServiceException {
        try {
            return couponDAO.getCoupons();
        } catch (DAOException e) {
            log.error("Error while getting coupons", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<Coupon> getCoupons(int pageNumber, int pageSize) throws ServiceException {
        try {
            return couponDAO.getCoupons(pageNumber, pageSize);
        } catch (DAOException e) {
            log.error("Error while getting coupons", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void createCoupon(String couponName, int code, float discount) throws ServiceException {
        Coupon coupon = Coupon.builder().name(couponName).code(code).discount(discount).build();
        try {
            couponDAO.save(coupon);
        } catch (DAOException e) {
            log.error("Error while creating coupon", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteCoupon(int id) throws ServiceException {
        try {
            couponDAO.delete(id);
        } catch (DAOException e) {
            log.error("Error while deleting coupon", e);
            throw new ServiceException(e);
        }
    }
}
