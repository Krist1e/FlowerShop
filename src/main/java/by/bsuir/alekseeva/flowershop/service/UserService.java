package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String name) throws ServiceException;
    Optional<User> getUserById(int id) throws ServiceException;
    List<User> getUsers() throws ServiceException;
    Page<User> getUsers(int pageNumber, int pageSize) throws ServiceException;
    void addUser(String username, String password, String email) throws ServiceException;
    void banUser(int id) throws ServiceException;
    void unbanUser(int id) throws ServiceException;
}
