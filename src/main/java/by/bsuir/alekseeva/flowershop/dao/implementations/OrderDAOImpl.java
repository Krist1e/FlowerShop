package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.dao.OrderDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OrderDAOImpl implements OrderDAO {
    public static final String SAVE_ORDER_SQL = "INSERT INTO `order` (user_id, total_price, order_datetime, address, telephone, comments, order_status_id, coupon_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, (SELECT id FROM order_status WHERE name = ?), ?)";
    public static final String SAVE_ITEM_SQL = "INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    public static final String GET_BY_ID_SQL = "SELECT * FROM order_view WHERE order_id = ?";
    public static final String GET_BY_USER_ID_SQL = "SELECT * FROM order_view WHERE user_id = ?";
    public static final String GET_BY_USER_ID_PAGE_SQL = "SELECT ov.* " +
            "FROM order_view ov JOIN ( " +
            "SELECT DISTINCT order_id " +
            "FROM order_view " +
            "WHERE user_id = ? " +
            "ORDER BY order_id " +
            "LIMIT ? OFFSET ? " +
            ") AS unique_orders ON ov.order_id = unique_orders.order_id " +
            "ORDER BY ov.order_id, ov.item_id";
    public static final String GET_ALL_SQL = "SELECT * FROM order_view";
    public static final String GET_ALL_PAGE_SQL = "SELECT * FROM order_view LIMIT ? OFFSET ?";
    public static final String GET_COUNT_SQL = "SELECT COUNT(*) FROM `order`";
    private static final String GET_COUNT_BY_USER_ID_SQL = "SELECT COUNT(*) FROM `order` WHERE user_id = ?";

    private final ConnectionPool connectionPool;

    @Override
    public void save(Order order) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            save(order, connection);
        } catch (SQLException e) {
            log.error("Failed to save order", e);
            throw new DAOException("Failed to save order", e);
        }
    }

    private void save(Order order, Connection connection) throws SQLException, DAOException {
        connection.setAutoCommit(false);
        try (PreparedStatement orderStatement = connection.prepareStatement(SAVE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            orderStatement.setInt(1, order.getUser().getId());
            orderStatement.setFloat(2, order.getTotalPrice());
            orderStatement.setTimestamp(3, Timestamp.valueOf(order.getDate()));
            orderStatement.setString(4, order.getAddress());
            orderStatement.setString(5, order.getPhone());
            orderStatement.setString(6, order.getComments());
            orderStatement.setString(7, order.getStatus().name());
            if (order.getCoupon() == null) {
                orderStatement.setNull(8, Types.INTEGER);
            } else {
                orderStatement.setInt(8, order.getCoupon().getId());
            }

            int affectedRows = orderStatement.executeUpdate();

            if (affectedRows == 0) {
                log.error("Inserting order failed, no rows affected.");
                throw new SQLException("Inserting order failed, no rows affected.");
            }

            saveItems(order, connection, orderStatement);
        } catch (SQLException e) {
            connection.rollback();
            log.error("Failed to save order", e);
            throw new DAOException("Failed to save order", e);
        }
    }

    private static void saveItems(Order order, Connection connection, PreparedStatement orderStatement) throws SQLException {
        try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                try (PreparedStatement orderItemStatement = connection.prepareStatement(SAVE_ITEM_SQL)) {
                    for (Item item : order.getOrderItems()) {
                        orderItemStatement.setInt(1, orderId);
                        orderItemStatement.setInt(2, item.getProduct().getId());
                        orderItemStatement.setInt(3, item.getQuantity());
                        orderItemStatement.setFloat(4, item.getPrice());
                        orderItemStatement.addBatch();
                    }
                    orderItemStatement.executeBatch();
                }
                connection.commit();
            } else {
                log.error("Inserting order failed, no ID obtained.");
                throw new SQLException("Inserting order failed, no ID obtained.");
            }
        } catch (SQLException e) {
            log.error("Failed to save order", e);
            throw new SQLException("Failed to save order", e);
        }
    }

    @Override
    public Optional<Order> getOrderById(int id) throws DAOException {
        Optional<Order> order;
        try (Connection connection = connectionPool.getConnection()) {
            order = getOrderById(id, connection);
        } catch (SQLException e) {
            log.error("Failed to get order by id", e);
            throw new DAOException("Failed to get order by id", e);
        }
        return order;
    }

    private Optional<Order> getOrderById(int id, Connection connection) throws DAOException {
        Optional<Order> order;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_ID_SQL)) {
            statement.setInt(1, id);
            order = getOrderById(statement);
        } catch (SQLException e) {
            log.error("Failed to get order by id", e);
            throw new DAOException("Failed to get order by id", e);
        }
        return order;
    }

    private Optional<Order> getOrderById(PreparedStatement statement) throws DAOException {
        Optional<Order> order = Optional.empty();
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                order = Optional.of(OrderMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            log.error("Failed to get order", e);
            throw new DAOException("Failed to get order", e);
        }
        return order;
    }


    @Override
    public List<Order> getOrdersByUserId(int userId) throws DAOException {
        List<Order> orders;
        try (Connection connection = connectionPool.getConnection()) {
            orders = getOrdersByUserId(userId, connection);
        } catch (SQLException e) {
            log.error("Failed to get orders by user id", e);
            throw new DAOException("Failed to get orders by user id", e);
        }
        return orders;
    }

    private List<Order> getOrdersByUserId(int userId, Connection connection) throws DAOException {
        List<Order> orders;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            orders = getOrders(statement);
        } catch (SQLException e) {
            log.error("Failed to get orders by user id", e);
            throw new DAOException("Failed to get orders by user id", e);
        }
        return orders;
    }

    @Override
    public Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize) throws DAOException {
        Page<Order> orders;
        try (Connection connection = connectionPool.getConnection()) {
            orders = getOrdersByUserId(userId, pageNumber, pageSize, connection);
        } catch (SQLException e) {
            log.error("Failed to get orders by user id", e);
            throw new DAOException("Failed to get orders by user id", e);
        }
        return orders;
    }

    private Page<Order> getOrdersByUserId(int userId, int pageNumber, int pageSize, Connection connection) throws DAOException {
        Page<Order> orders;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_USER_ID_PAGE_SQL)) {
            statement.setInt(1, userId);
            statement.setInt(2, pageSize);
            statement.setInt(3, (pageNumber - 1) * pageSize);
            orders = getOrderPage(statement, pageNumber, pageSize, getOrdersCountByUserId(userId, connection));
        } catch (SQLException e) {
            log.error("Failed to get orders by user id", e);
            throw new DAOException("Failed to get orders by user id", e);
        }
        return orders;
    }

    private List<Order> getOrders(PreparedStatement statement) throws DAOException {
        List<Order> orders;
        try (ResultSet resultSet = statement.executeQuery()) {
            orders = OrderMapper.toEntityList(resultSet);
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    private Page<Order> getOrderPage(PreparedStatement statement, int pageNumber, int pageSize, int totalRecords) throws DAOException {
        Page<Order> orders;
        try (ResultSet resultSet = statement.executeQuery()) {
            orders = OrderMapper.toEntityPage(resultSet, pageNumber, pageSize, totalRecords);
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrders() throws DAOException {
        List<Order> orders;
        try (Connection connection = connectionPool.getConnection()) {
            orders = getOrders(connection);
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    private List<Order> getOrders(Connection connection) throws DAOException {
        List<Order> orders;
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_SQL)) {
            orders = getOrders(statement);
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    @Override
    public Page<Order> getOrders(int pageNumber, int pageSize) throws DAOException {
        Page<Order> orders;
        try (Connection connection = connectionPool.getConnection()) {
            orders = getOrders(pageNumber, pageSize, connection);
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    private Page<Order> getOrders(int pageNumber, int pageSize, Connection connection) throws DAOException {
        Page<Order> orders;
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_PAGE_SQL)) {
            statement.setInt(1, pageSize);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            orders = getOrderPage(statement, pageNumber, pageSize, getOrdersCount(connection));
        } catch (SQLException e) {
            log.error("Failed to get orders", e);
            throw new DAOException("Failed to get orders", e);
        }
        return orders;
    }

    private int getOrdersCount(Connection connection) throws DAOException {
        int totalRecords;
        try (PreparedStatement statement = connection.prepareStatement(GET_COUNT_SQL)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            totalRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error("Failed to get orders count", e);
            throw new DAOException("Failed to get orders count", e);
        }
        return totalRecords;
    }

    private int getOrdersCountByUserId(int userId, Connection connection) throws DAOException {
        int totalRecords;
        try (PreparedStatement statement = connection.prepareStatement(GET_COUNT_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            totalRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error("Failed to get orders count", e);
            throw new DAOException("Failed to get orders count", e);
        }
        return totalRecords;
    }
}
