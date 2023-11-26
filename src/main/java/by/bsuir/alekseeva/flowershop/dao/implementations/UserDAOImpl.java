package by.bsuir.alekseeva.flowershop.dao.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.dao.RoleDAO;
import by.bsuir.alekseeva.flowershop.dao.UserDAO;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;
    private final RoleDAO roleDAO;

    @Override
    public Optional<User> getUserByUsername(String name) throws DAOException {
        Optional<User> user = Optional.empty();
        try(Connection connection = connectionPool.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?")) {
                statement.setString(1, name);
                try(var resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = Optional.of(UserMapper.toEntity(resultSet, roleDAO));
                    }
                }
            } catch (SQLException e) {
                log.error("Failed to get user by username", e);
                throw new DAOException("Failed to get user by username", e);
            }
        } catch (SQLException e) {
            log.error("Failed to get user by username", e);
            throw new DAOException("Failed to get user by username", e);
        }
        return user;
    }

    @Override
    public Optional<User> getUserById(int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<User> getUsers() throws DAOException {
        return null;
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) throws DAOException {
        return null;
    }

    @Override
    public void saveUser(User user) throws DAOException {

    }

    @Override
    public void updateUser(User user) throws DAOException {

    }
}
