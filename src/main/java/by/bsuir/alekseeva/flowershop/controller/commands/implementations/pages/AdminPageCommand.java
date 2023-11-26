package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class AdminPageCommand implements Command {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    /*@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       try {
           request.setAttribute("users", userService.getUsers());
       } catch (Exception e) {
           throw new ServletException(e.getMessage());
       }
        return "WEB-INF/view/" + CommandName.ADMIN_PAGE + ".jsp";
    }*/

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        return new ViewResult("admin");
    }
}
