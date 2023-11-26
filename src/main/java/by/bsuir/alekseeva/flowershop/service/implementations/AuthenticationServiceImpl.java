package by.bsuir.alekseeva.flowershop.service.implementations;

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
                            .message("Successful authentication")
                            .build();
                } else {
                    return AuthenticationResult.builder()
                            .success(false)
                            .message("Wrong password")
                            .build();
                }
            } else {
                return AuthenticationResult.builder()
                        .success(false)
                        .message("User with this username doesn't exist")
                        .build();
            }
        } catch (Exception e) {
            return AuthenticationResult.builder()
                    .success(false)
                    .message("Something went wrong")
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
                        .message("User with this username already exists")
                        .build();
            } else {
                String passwordHash = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
                userService.addUser(username, passwordHash, email);
                return RegistrationResult.builder()
                        .success(true)
                        .message("Successful registration")
                        .build();
            }
        } catch (Exception e) {
            return RegistrationResult.builder()
                    .success(false)
                    .message("Something went wrong")
                    .build();
        }
    }
}
