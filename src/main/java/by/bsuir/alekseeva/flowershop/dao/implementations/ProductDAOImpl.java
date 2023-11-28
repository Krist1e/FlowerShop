package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.dao.ProductDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.ProductMapper;
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
public class ProductDAOImpl implements ProductDAO {
    private final ConnectionPool connectionPool;

    @Override
    public Optional<Product> getProductById(int id) throws DAOException {
        Optional<Product> product;
        try (Connection connection = connectionPool.getConnection()) {
            product = getProduct(id, connection);
        } catch (SQLException e) {
            log.error("Failed to get product by id", e);
            throw new DAOException("Failed to get product by id", e);
        }
        return product;
    }

    private Optional<Product> getProduct(int id, Connection connection) throws DAOException {
        Optional<Product> product;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?")) {
            statement.setInt(1, id);
            product = getProduct(statement);
        } catch (SQLException e) {
            log.error("Failed to get product by id", e);
            throw new DAOException("Failed to get product by id", e);
        }
        return product;
    }

    private Optional<Product> getProduct(PreparedStatement statement) throws DAOException {
        Optional<Product> product = Optional.empty();
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                product = Optional.of(ProductMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            log.error("Failed to get product", e);
            throw new DAOException("Failed to get product", e);
        }
        return product;
    }

    @Override
    public Optional<Product> getProductByName(String name) throws DAOException {
        Optional<Product> product;
        try (Connection connection = connectionPool.getConnection()) {
            product = getProduct(name, connection);
        } catch (SQLException e) {
            log.error("Failed to get product by name", e);
            throw new DAOException("Failed to get product by name", e);
        }
        return product;
    }

    private Optional<Product> getProduct(String name, Connection connection) throws DAOException {
        Optional<Product> product;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE name = ?")) {
            statement.setString(1, name);
            product = getProduct(statement);
        } catch (SQLException e) {
            log.error("Failed to get product by name", e);
            throw new DAOException("Failed to get product by name", e);
        }
        return product;
    }

    @Override
    public List<Product> getInStockProducts() throws DAOException {
        List<Product> products;
        try (Connection connection = connectionPool.getConnection()) {
            products = getInStockProducts(connection);
        } catch (SQLException e) {
            log.error("Failed to get in stock products", e);
            throw new DAOException("Failed to get in stock products", e);
        }
        return products;
    }

    private List<Product> getInStockProducts(Connection connection) throws DAOException {
        List<Product> products;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE is_in_stock = true")) {
            products = ProductMapper.toEntityList(statement.executeQuery());
        } catch (SQLException e) {
            log.error("Failed to get in stock products", e);
            throw new DAOException("Failed to get in stock products", e);
        }
        return products;
    }

    @Override
    public Page<Product> getInStockProducts(int pageNumber, int pageSize) throws DAOException {
        List<Product> products;
        int totalProducts;
        try (Connection connection = connectionPool.getConnection()) {
            products = getInStockProducts(pageNumber, pageSize, connection);
            totalProducts = getInStockProducts(connection).size();
        } catch (SQLException e) {
            log.error("Failed to get in stock products", e);
            throw new DAOException("Failed to get in stock products", e);
        }
        return Page.<Product>builder()
                .content(products)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(totalProducts)
                .totalPages((int) Math.ceil((double) totalProducts / pageSize))
                .build();
    }

    private List<Product> getInStockProducts(int pageNumber, int pageSize, Connection connection) throws DAOException {
        List<Product> products;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE is_in_stock = true LIMIT ? OFFSET ?")) {
            statement.setInt(1, pageSize);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            products = ProductMapper.toEntityList(statement.executeQuery());
        } catch (SQLException e) {
            log.error("Failed to get in stock products", e);
            throw new DAOException("Failed to get in stock products", e);
        }
        return products;
    }

    @Override
    public void save(Product product) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            save(product, connection);
        } catch (SQLException e) {
            log.error("Failed to save product", e);
            throw new DAOException("Failed to save product", e);
        }
    }

    private void save(Product product, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO product (name, description, price, discount, imagePath, is_in_stock) VALUES (?, ?, ?, ?, ?, ?)")) {
            setProductFields(product, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to save product", e);
            throw new DAOException("Failed to save product", e);
        }
    }

    @Override
    public void update(Product product) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            update(product, connection);
        } catch (SQLException e) {
            log.error("Failed to update product", e);
            throw new DAOException("Failed to update product", e);
        }
    }

    private void update(Product product, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE product SET name = ?, description = ?, price = ?, discount = ?, imagePath = ?, is_in_stock = ? WHERE id = ?")) {
            setProductFields(product, statement);
            statement.setInt(7, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update product", e);
            throw new DAOException("Failed to update product", e);
        }
    }

    private void setProductFields(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getDescription());
        statement.setFloat(3, product.getPrice());
        statement.setFloat(4, product.getDiscount());
        statement.setString(5, product.getImagePath());
        statement.setBoolean(6, true);
    }

    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            delete(id, connection);
        } catch (SQLException e) {
            log.error("Failed to delete product", e);
            throw new DAOException("Failed to delete product", e);
        }
    }

    private void delete(int id, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE product SET is_in_stock = false WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to delete product", e);
            throw new DAOException("Failed to delete product", e);
        }
    }
}
