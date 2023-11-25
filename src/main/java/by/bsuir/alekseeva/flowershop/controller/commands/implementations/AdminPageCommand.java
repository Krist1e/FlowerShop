package by.bsuir.alekseeva.flowershop.controller.commands.implementations;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AdminPageCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       try {
           request.setAttribute("users", userService.getUsers());
       } catch (Exception e) {
           throw new ServletException(e.getMessage());
       }
        return "WEB-INF/view/" + CommandName.ADMIN_PAGE + ".jsp";
    }
}
