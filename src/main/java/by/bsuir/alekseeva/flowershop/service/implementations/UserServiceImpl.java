package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final List<User> users = new ArrayList<>();

    private final ShoppingCartService shoppingCartService;

    @Override
    public Optional<User> getUserByUsername(String name) {
        return users.stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public Page<User> getUsers(int pageNumber, int pageSize) {
        return users.stream().collect(Page.toPage(pageNumber, pageSize));
    }

    @Override
    public void addUser(String username, String password, String email) {
        users.add(User.builder()
                .id(users.size() + 1)
                .username(username)
                .password(password)
                .email(email)
                .role(Role.USER)
                .build());
        shoppingCartService.createCart(users.size());
    }

    @Override
    public void banUser(int id) {
        getUserById(id).ifPresent(user -> user.setRole(Role.BANNED_USER));
    }

    @Override
    public void unbanUser(int id) {
        getUserById(id).ifPresent(user -> user.setRole(Role.USER));
    }
}
