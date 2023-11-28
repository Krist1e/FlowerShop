package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Coupon;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.CouponDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.CouponMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CouponDAOImpl implements CouponDAO {
    private final ConnectionPool connectionPool;
    @Override
    public Optional<Coupon> getCouponById(int id) throws DAOException {
        Optional<Coupon> coupon;
        try (Connection connection = connectionPool.getConnection()) {
            coupon = getCoupon(id, connection);
        } catch (SQLException e) {
            log.error("Failed to get coupon by id", e);
            throw new DAOException("Failed to get coupon by id", e);
        }
        return coupon;
    }

    private Optional<Coupon> getCoupon(int id, Connection connection) throws DAOException {
        Optional<Coupon> coupon;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM coupon WHERE id = ?")) {
            statement.setInt(1, id);
            coupon = getCoupon(statement);
        } catch (SQLException e) {
            log.error("Failed to get coupon by id", e);
            throw new DAOException("Failed to get coupon by id", e);
        }
        return coupon;
    }

    private Optional<Coupon> getCoupon(PreparedStatement statement) throws DAOException {
        Optional<Coupon> coupon = Optional.empty();
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                coupon = Optional.of(CouponMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            log.error("Failed to get coupon", e);
            throw new DAOException("Failed to get coupon", e);
        }
        return coupon;
    }

    @Override
    public Optional<Coupon> getCouponByCode(int code) throws DAOException {
        Optional<Coupon> coupon;
        try (Connection connection = connectionPool.getConnection()) {
            coupon = getCouponByCode(code, connection);
        } catch (SQLException e) {
            log.error("Failed to get coupon by code", e);
            throw new DAOException("Failed to get coupon by code", e);
        }
        return coupon;
    }

    private Optional<Coupon> getCouponByCode(int code, Connection connection) throws DAOException {
        Optional<Coupon> coupon;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM coupon WHERE code = ?")) {
            statement.setInt(1, code);
            coupon = getCoupon(statement);
        } catch (SQLException e) {
            log.error("Failed to get coupon by code", e);
            throw new DAOException("Failed to get coupon by code", e);
        }
        return coupon;
    }

    @Override
    public List<Coupon> getCoupons() throws DAOException {
        List<Coupon> coupons;
        try (Connection connection = connectionPool.getConnection()) {
            coupons = getCoupons(connection);
        } catch (SQLException e) {
            log.error("Failed to get coupons", e);
            throw new DAOException("Failed to get coupons", e);
        }
        return coupons;
    }

    private List<Coupon> getCoupons(Connection connection) throws DAOException {
        List<Coupon> coupons;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM coupon")) {
            coupons = CouponMapper.toEntityList(statement.executeQuery());
        } catch (SQLException e) {
            log.error("Failed to get coupons", e);
            throw new DAOException("Failed to get coupons", e);
        }
        return coupons;
    }

    @Override
    public Page<Coupon> getCoupons(int pageNumber, int pageSize) throws DAOException {
        List<Coupon> coupons;
        int numberOfCoupons;
        try (Connection connection = connectionPool.getConnection()) {
            coupons = getCoupons(connection, pageNumber, pageSize);
            numberOfCoupons = getNumberOfCoupons(connection);
        } catch (SQLException e) {
            log.error("Failed to get coupons", e);
            throw new DAOException("Failed to get coupons", e);
        }
        return Page.<Coupon>builder()
                .content(coupons)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(numberOfCoupons)
                .totalPages((int) Math.ceil((double) numberOfCoupons / pageSize))
                .build();
    }

    private int getNumberOfCoupons(Connection connection) throws DAOException {
        int numberOfCoupons;
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM coupon")) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            numberOfCoupons = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error("Failed to get number of coupons", e);
            throw new DAOException("Failed to get number of coupons", e);
        }
        return numberOfCoupons;
    }

    private List<Coupon> getCoupons(Connection connection, int pageNumber, int pageSize) throws DAOException {
        List<Coupon> coupons;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM coupon LIMIT ? OFFSET ?")) {
            statement.setInt(1, pageSize);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            coupons = CouponMapper.toEntityList(statement.executeQuery());
        } catch (SQLException e) {
            log.error("Failed to get coupons", e);
            throw new DAOException("Failed to get coupons", e);
        }
        return coupons;
    }

    @Override
    public void save(Coupon coupon) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            save(coupon, connection);
        } catch (SQLException e) {
            log.error("Failed to save coupon", e);
            throw new DAOException("Failed to save coupon", e);
        }
    }

    private void save(Coupon coupon, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO coupon (code, name, discount) VALUES (?, ?, ?)")) {
            statement.setInt(1, coupon.getCode());
            statement.setString(2, coupon.getName());
            statement.setFloat(3, coupon.getDiscount());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to save coupon", e);
            throw new DAOException("Failed to save coupon", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            delete(id, connection);
        } catch (SQLException e) {
            log.error("Failed to delete coupon", e);
            throw new DAOException("Failed to delete coupon", e);
        }
    }

    private void delete(int id, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM coupon WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to delete coupon", e);
            throw new DAOException("Failed to delete coupon", e);
        }
    }
}
