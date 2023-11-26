package by.bsuir.alekseeva.flowershop.service;

import lombok.Builder;
import lombok.Data;

public interface AuthenticationService {
    @Data
    @Builder
    class AuthenticationResult {
        private boolean success;
        private String message;
    }

    @Data
    @Builder
    class RegistrationResult {
        private boolean success;
        private String message;
    }

    AuthenticationResult authenticate(String username, String password);
    RegistrationResult register(String email, String username, String password);
}
