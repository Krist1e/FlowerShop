package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public class CouponDAOImpl implements CouponDAO {
    @Override
    public Optional<Coupon> getCouponById(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> getCouponByCode(int code) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Coupon> getCoupons() throws DAOException {
        return null;
    }

    @Override
    public Page<Coupon> getCoupons(int pageNumber, int pageSize) throws DAOException {
        return null;
    }

    @Override
    public void save(Coupon coupon) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }
}
