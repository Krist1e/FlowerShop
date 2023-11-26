package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.dao.ShoppingCartDAO;
import by.bsuir.alekseeva.flowershop.dao.UserDAO;
import by.bsuir.alekseeva.flowershop.exception.DAOException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ShoppingCartDAO shoppingCartDAO;
    private final UserDAO userDAO;

    @Override
    public Optional<User> getUserByUsername(String name) throws ServiceException {
        try {
            return userDAO.getUserByUsername(name);
        } catch (DAOException e) {
            log.error("Error while getting user by username", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> getUserById(int id) throws ServiceException {
        try {
            return userDAO.getUserById(id);
        } catch (DAOException e) {
            log.error("Error while getting user by id", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getUsers() throws ServiceException {
        try {
            return userDAO.getUsers();
        } catch (DAOException e) {
            log.error("Error while getting users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) throws ServiceException {
        try {
            return userDAO.getUsers(pageNumber, pageSize);
        } catch (DAOException e) {
            log.error("Error while getting users", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void addUser(String username, String password, String email) throws ServiceException {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(Role.USER)
                .build();
        try {
            userDAO.saveUser(user);
        } catch (DAOException e) {
            log.error("Error while saving user", e);
            throw new ServiceException(e);
        }
        ShoppingCart cart = createCart(username);
        try {
            shoppingCartDAO.save(cart);
        } catch (DAOException e) {
            log.error("Error while saving shopping cart", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void banUser(int id) throws ServiceException {
        User user = getUser(id);
        user.setRole(Role.BANNED_USER);
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            log.error("Error while updating user", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void unbanUser(int id) throws ServiceException {
        User user = getUser(id);
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            log.error("Error while updating user", e);
            throw new ServiceException(e);
        }
    }

    private ShoppingCart createCart(String username) throws ServiceException {
        User savedUser = getUserByUsername(username).orElseThrow(() -> new ServiceException("User not found"));
        return ShoppingCart.builder()
                .user(savedUser)
                .cartItems(new ArrayList<>())
                .coupon(null)
                .totalPrice(0)
                .build();
    }

    private User getUser(int id) throws ServiceException {
        Optional<User> user = getUserById(id);
        if (user.isEmpty()) {
            throw new ServiceException("User not found");
        }
        return user.get();
    }
}
