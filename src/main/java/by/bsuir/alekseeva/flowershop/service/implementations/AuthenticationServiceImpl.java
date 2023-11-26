package by.bsuir.alekseeva.flowershop.service.implementations;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
        register("admin@", "admin", "123");
        register("user@", "user", "123");
        User user = userService.getUserByUsername("admin").get();
        user.setRole(Role.ADMIN);
    }

    @Override
    public AuthenticationResult authenticate(String username, String password) {
        try {
            Optional<User> user = userService.getUserByUsername(username);
            String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            if (user.isPresent()) {
                if (user.get().getPassword().equals(passwordHash)) {
                    return AuthenticationResult.builder()
                            .success(true)
                            .message("success")
                            .build();
                } else {
                    return AuthenticationResult.builder()
                            .success(false)
                            .message("wrong_password")
                            .build();
                }
            } else {
                return AuthenticationResult.builder()
                        .success(false)
                        .message("no_such_user")
                        .build();
            }
        } catch (Exception e) {
            return AuthenticationResult.builder()
                    .success(false)
                    .message("something_went_wrong")
                    .build();
        }
    }

    @Override
    public RegistrationResult register(String email, String username, String password) {
        try {
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                return RegistrationResult.builder()
                        .success(false)
                        .message("user_already_exists")
                        .build();
            } else {
                String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
                userService.addUser(username, passwordHash, email);
                return RegistrationResult.builder()
                        .success(true)
                        .message("success")
                        .build();
            }
        } catch (Exception e) {
            return RegistrationResult.builder()
                    .success(false)
                    .message("something_went_wrong")
                    .build();
        }
    }
}
