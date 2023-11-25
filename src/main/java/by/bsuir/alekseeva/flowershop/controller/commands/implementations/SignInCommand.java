package by.bsuir.alekseeva.flowershop.controller.commands.implementations;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.service.AuthenticationService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignInCommand implements Command {

    private final AuthenticationService authenticationService = ServiceFactory.getInstance().getAuthenticationService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AuthenticationService.AuthenticationResult result = authenticationService.authenticate(username, password);
        if (result.isSuccess()) {
            request.getSession().setAttribute("username", username);
            return "/WEB-INF/view/catalog.jsp";
        } else {
            request.setAttribute("error", result.getMessage());
            return "/WEB-INF/view/" + CommandName.SIGN_IN_PAGE + ".jsp";
        }
    }
}
