package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.ShoppingCartMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
    public static final String GET_BY_USER_ID_SQL = "SELECT * FROM cart_view WHERE user_id = ?";
    private static final String GET_BY_PRODUCT_ID_SQL = "SELECT cv.* " +
            "FROM cart_view cv " +
            "         JOIN ( " +
            "    SELECT DISTINCT cart_id " +
            "    FROM cart_view " +
            "    WHERE product_id = ? " +
            "    ORDER BY cart_id " +
            ") AS filtered_carts ON cv.cart_id = filtered_carts.cart_id " +
            "ORDER BY cv.cart_id, cv.item_id";
    public static final String SAVE_CART_ITEM_SQL = "INSERT INTO cart_item (shopping_cart_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    public static final String SAVE_CART_SQL = "INSERT INTO shopping_cart (user_id, total_price, coupon_id) VALUES (?, ?, ?)";
    public static final String DELETE_CART_ITEM_SQL = "DELETE FROM cart_item WHERE shopping_cart_id = ?";
    public static final String UPDATE_CART_SQL = "UPDATE shopping_cart SET total_price = ?, coupon_id = ? WHERE user_id = ?";
    private final ConnectionPool connectionPool;

    @Override
    public Optional<ShoppingCart> getCartByUserId(int userId) throws DAOException {
        Optional<ShoppingCart> shoppingCart;
        try (Connection connection = connectionPool.getConnection()) {
            shoppingCart = getShoppingCart(userId, connection);
        } catch (SQLException e) {
            log.error("Failed to get shopping cart by user id", e);
            throw new DAOException("Failed to get shopping cart by user id", e);
        }
        return shoppingCart;
    }

    private Optional<ShoppingCart> getShoppingCart(int userId, Connection connection) throws DAOException {
        Optional<ShoppingCart> shoppingCart;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_USER_ID_SQL)) {
            statement.setInt(1, userId);
            shoppingCart = getShoppingCart(statement);
        } catch (SQLException e) {
            log.error("Failed to get shopping cart by user id", e);
            throw new DAOException("Failed to get shopping cart by user id", e);
        }
        return shoppingCart;
    }

    private Optional<ShoppingCart> getShoppingCart(PreparedStatement statement) throws DAOException {
        Optional<ShoppingCart> shoppingCart = Optional.empty();
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                shoppingCart = Optional.of(ShoppingCartMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            log.error("Failed to get shopping cart", e);
            throw new DAOException("Failed to get shopping cart", e);
        }
        return shoppingCart;
    }

    @Override
    public List<ShoppingCart> getCartsByProductId(int productId) throws DAOException {
        List<ShoppingCart> shoppingCarts;
        try (Connection connection = connectionPool.getConnection()) {
            shoppingCarts = getCartsByProductId(productId, connection);
        } catch (SQLException e) {
            log.error("Failed to get shopping carts by product id", e);
            throw new DAOException("Failed to get shopping carts by product id", e);
        }
        return shoppingCarts;
    }

    private List<ShoppingCart> getCartsByProductId(int productId, Connection connection) throws DAOException {
        List<ShoppingCart> shoppingCarts;
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_PRODUCT_ID_SQL)) {
            statement.setInt(1, productId);
            shoppingCarts = getCartsByProductId(statement);
        } catch (SQLException e) {
            log.error("Failed to get shopping carts by product id", e);
            throw new DAOException("Failed to get shopping carts by product id", e);
        }
        return shoppingCarts;
    }

    private List<ShoppingCart> getCartsByProductId(PreparedStatement statement) throws DAOException {
        List<ShoppingCart> shoppingCarts;
        try (ResultSet resultSet = statement.executeQuery()) {
            shoppingCarts = ShoppingCartMapper.toEntityList(resultSet);
        } catch (SQLException e) {
            log.error("Failed to get shopping carts by product id", e);
            throw new DAOException("Failed to get shopping carts by product id", e);
        }
        return shoppingCarts;
    }

    @Override
    public void save(ShoppingCart shoppingCart) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            saveShoppingCart(shoppingCart, connection);
        } catch (SQLException e) {
            log.error("Failed to save shopping cart", e);
            throw new DAOException("Failed to save shopping cart", e);
        }
    }

    private void saveShoppingCart(ShoppingCart shoppingCart, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(SAVE_CART_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, shoppingCart.getUser().getId());
            statement.setFloat(2, shoppingCart.getTotalPrice());
            if (shoppingCart.getCoupon() == null) {
                statement.setNull(3, Types.INTEGER);
            } else {
                statement.setInt(3, shoppingCart.getCoupon().getId());
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                log.error("Creating shopping cart failed, no rows affected.");
                throw new SQLException("Creating shopping cart failed, no rows affected.");
            }

            saveCartItems(shoppingCart, connection, statement);
        } catch (SQLException e) {
            connection.rollback();
            log.error("Failed to save shopping cart", e);
            throw new SQLException("Failed to save shopping cart", e);
        }
    }

    private void saveCartItems(ShoppingCart shoppingCart, Connection connection, PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int cartId = generatedKeys.getInt(1);

                try (PreparedStatement cartItemStatement = connection.prepareStatement(SAVE_CART_ITEM_SQL)) {
                    for (Item item : shoppingCart.getCartItems()) {
                        cartItemStatement.setInt(1, cartId);
                        cartItemStatement.setInt(2, item.getProduct().getId());
                        cartItemStatement.setInt(3, item.getQuantity());
                        cartItemStatement.setFloat(4, item.getPrice());
                        cartItemStatement.addBatch();
                    }

                    cartItemStatement.executeBatch();
                }

                connection.commit();
            } else {
                log.error("Inserting shopping cart failed, no ID obtained.");
                throw new SQLException("Inserting shopping cart failed, no ID obtained.");
            }
        } catch (SQLException e) {
            log.error("Failed to save shopping cart", e);
            throw new SQLException("Failed to save shopping cart", e);
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            updateShoppingCart(shoppingCart, connection);
        } catch (SQLException e) {
            log.error("Failed to update shopping cart", e);
            throw new DAOException("Failed to update shopping cart", e);
        }
    }

    private void updateShoppingCart(ShoppingCart shoppingCart, Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CART_SQL)) {
            statement.setFloat(1, shoppingCart.getTotalPrice());
            statement.setInt(3, shoppingCart.getUser().getId());

            if (shoppingCart.getCoupon() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, shoppingCart.getCoupon().getId());
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                log.error("Updating shopping cart failed, no rows affected.");
                throw new SQLException("Updating shopping cart failed, no rows affected.");
            }

            deleteCartItems(shoppingCart, connection);
            insertCartItems(shoppingCart, connection);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            log.error("Failed to update shopping cart", e);
            throw new SQLException("Failed to update shopping cart", e);
        }
    }

    private void insertCartItems(ShoppingCart shoppingCart, Connection connection) throws SQLException {
        try (PreparedStatement cartItemStatement = connection.prepareStatement(SAVE_CART_ITEM_SQL)) {
            for (Item item : shoppingCart.getCartItems()) {
                cartItemStatement.setInt(1, shoppingCart.getId());
                cartItemStatement.setInt(2, item.getProduct().getId());
                cartItemStatement.setInt(3, item.getQuantity());
                cartItemStatement.setFloat(4, item.getPrice());
                cartItemStatement.addBatch();
            }

            cartItemStatement.executeBatch();
        }
    }

    private void deleteCartItems(ShoppingCart shoppingCart, Connection connection) throws SQLException {
        try (PreparedStatement deleteCartItemStatement = connection.prepareStatement(DELETE_CART_ITEM_SQL)) {
            deleteCartItemStatement.setInt(1, shoppingCart.getId());
            deleteCartItemStatement.executeUpdate();
        }
    }
}
