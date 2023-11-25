package by.bsuir.alekseeva.flowershop.controller.commands.implementations;

import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class SignUpCommand implements Command {
    private final AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        AuthenticationService.RegistrationResult result = authenticationService.register(email, username, password);
        if (result.isSuccess()) {
            Optional<User> user = userService.getUserByUsername(username);
            request.getSession().setAttribute("user", user.get());
            request.getSession().setAttribute("role", user.get().getRole());
            return "/WEB-INF/view/signIn.jsp";
        } else {
            request.setAttribute("error", result.getMessage());
            return "sign-up";
        }
    }
}
