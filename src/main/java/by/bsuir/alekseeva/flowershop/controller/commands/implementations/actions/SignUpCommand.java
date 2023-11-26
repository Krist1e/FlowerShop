package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignUpCommand implements Command {
    private final AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        log.info("User with email {} and username {} is trying to register", email, username);

        AuthenticationService.RegistrationResult result = authenticationService.register(email, username, password);
        log.info("Registration result: {}", result);
        if (result.isSuccess()) {
            return new RedirectResult(CommandName.SIGN_IN_PAGE);
        } else {
            request.setAttribute("error", result.getMessage());
            return new ViewResult("signUp");
        }
    }
}
