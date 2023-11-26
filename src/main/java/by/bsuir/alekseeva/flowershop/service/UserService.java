package by.bsuir.alekseeva.flowershop.service;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String name);
    Optional<User> getUserById(int id);
    List<User> getUsers();
    Page<User> getUsers(int pageNumber, int pageSize);
    void addUser(String username, String password, String email);
    void banUser(int id);
    void unbanUser(int id);
/*    void addCoupon(User user);
    void deleteCoupon(int id);*/
}
