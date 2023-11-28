package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.dao.UserDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.UserMapper;
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
public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;

    @Override
    public Optional<User> getUserByUsername(String name) throws DAOException {
        Optional<User> user;
        try (Connection connection = connectionPool.getConnection()) {
            user = getUserByUsername(name, connection);
        } catch (SQLException e) {
            log.error("Failed to get user by username", e);
            throw new DAOException("Failed to get user by username", e);
        }
        return user;
    }

    private Optional<User> getUserByUsername(String name, Connection connection) throws DAOException {
        Optional<User> user;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_view WHERE username = ?")) {
            statement.setString(1, name);
            user = getUserByUsername(statement);
        } catch (SQLException e) {
            log.error("Failed to get user by username", e);
            throw new DAOException("Failed to get user by username", e);
        }
        return user;
    }

    private Optional<User> getUserByUsername(PreparedStatement statement) throws DAOException {
        Optional<User> user = Optional.empty();
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                user = Optional.of(UserMapper.toEntity(resultSet));
            }
        } catch (SQLException e) {
            log.error("Failed to get user by username", e);
            throw new DAOException("Failed to get user by username", e);
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) throws DAOException {
        Optional<User> user;
        try (Connection connection = connectionPool.getConnection()) {
            user = getUserById(id, connection);
        } catch (SQLException e) {
            log.error("Failed to get user by id", e);
            throw new DAOException("Failed to get user by id", e);
        }
        return user;
    }

    private Optional<User> getUserById(int id, Connection connection) throws DAOException {
        Optional<User> user;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_view WHERE id = ?")) {
            statement.setInt(1, id);
            user = getUserByUsername(statement);
        } catch (SQLException e) {
            log.error("Failed to get user by id", e);
            throw new DAOException("Failed to get user by id", e);
        }
        return user;
    }

    @Override
    public List<User> getUsers() throws DAOException {
        List<User> users;
        try (Connection connection = connectionPool.getConnection()) {
            users = getUsers(connection);
        } catch (SQLException e) {
            log.error("Failed to get users", e);
            throw new DAOException("Failed to get users", e);
        }
        return users;
    }

    private List<User> getUsers(Connection connection) throws DAOException {
        List<User> users;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_view")) {
            users = getUsers(statement);
        } catch (SQLException e) {
            log.error("Failed to get users", e);
            throw new DAOException("Failed to get users", e);
        }
        return users;
    }

    private List<User> getUsers(PreparedStatement statement) throws DAOException {
        List<User> users;
        try (ResultSet resultSet = statement.executeQuery()) {
            users = UserMapper.toEntityList(resultSet);
        } catch (SQLException e) {
            log.error("Failed to get users", e);
            throw new DAOException("Failed to get users", e);
        }
        return users;
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) throws DAOException {
        List<User> users;
        int numberOfUsers;
        try (Connection connection = connectionPool.getConnection()) {
            users = getUsers(pageNumber, pageSize, connection);
            numberOfUsers = getNumberOfUsers(connection);
        } catch (SQLException e) {
            log.error("Failed to get users", e);
            throw new DAOException("Failed to get users", e);
        }
        return Page.<User>builder().content(users).pageNumber(pageNumber).pageSize(pageSize).totalPages((int) Math.ceil((double) numberOfUsers / pageSize)).totalElements(numberOfUsers).build();
    }

    private List<User> getUsers(int pageNumber, int pageSize, Connection connection) throws DAOException {
        List<User> users;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_view LIMIT ? OFFSET ?")) {
            statement.setInt(1, pageSize);
            statement.setInt(2, (pageNumber - 1) * pageSize);
            users = getUsers(statement);
        } catch (SQLException e) {
            log.error("Failed to get users", e);
            throw new DAOException("Failed to get users", e);
        }
        return users;
    }


    @Override
    public void saveUser(User user) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            saveUser(user, connection);
        } catch (SQLException e) {
            log.error("Failed to save user", e);
            throw new DAOException("Failed to save user", e);
        }
    }

    private void saveUser(User user, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO user (username, password, email, role_id) VALUES (?, ?, ?, (SELECT id FROM role WHERE name = ?))")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to save user", e);
            throw new DAOException("Failed to save user", e);
        }
    }

    @Override
    public void updateUser(User user) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            updateUser(user, connection);
        } catch (SQLException e) {
            log.error("Failed to update user", e);
            throw new DAOException("Failed to update user", e);
        }
    }

    private void updateUser(User user, Connection connection) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE user SET username = ?, password = ?, email = ?, role_id = (SELECT id FROM role WHERE name = ?) WHERE id = ?")) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().name());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update user", e);
            throw new DAOException("Failed to update user", e);
        }
    }

    private int getNumberOfUsers(Connection connection) throws DAOException {
        int numberOfUsers;
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM user")) {
            numberOfUsers = getNumberOfUsers(statement);
        } catch (SQLException e) {
            log.error("Failed to get number of users", e);
            throw new DAOException("Failed to get number of users", e);
        }
        return numberOfUsers;
    }

    private int getNumberOfUsers(PreparedStatement statement) throws DAOException {
        int numberOfUsers = 0;
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) numberOfUsers = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error("Failed to get number of users", e);
            throw new DAOException("Failed to get number of users", e);
        }
        return numberOfUsers;
    }
}
