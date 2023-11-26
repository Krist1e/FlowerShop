package by.bsuir.alekseeva.flowershop.dao;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> getUserByUsername(String name) throws DAOException;
    Optional<User> getUserById(int id) throws DAOException;
    List<User> getUsers() throws DAOException;
    Page<User> getUsers(int pageNumber, int pageSize) throws DAOException;
    void saveUser(User user) throws DAOException;
    void updateUser(User user) throws DAOException;
}
