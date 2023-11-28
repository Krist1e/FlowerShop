package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class SignInCommand implements Command {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("User with username {} is trying to sign in", username);
        AuthenticationService.AuthenticationResult result = authenticationService.authenticate(username, password);
        log.info("Authentication result: {}", result);
        if (result.isSuccess()) {
            Optional<User> user;
            try {
                user = userService.getUserByUsername(username);
            } catch (ServiceException e) {
                log.error("Failed to get user", e);
                throw new CommandException("Failed to get user", e);
            }
            if (user.isEmpty()) {
                throw new CommandException("No such user");
            }
            request.getSession().setAttribute("user", user.get());
            return new RedirectResult(CommandName.CATALOG_PAGE);
        } else {
            request.setAttribute("username", username);
            request.setAttribute("error", result.getMessage());
            return new ViewResult("signIn");
        }
    }
}
