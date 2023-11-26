package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class BanCommand implements Command {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String stringId = request.getParameter("id");
        if (stringId == null) {
            throw new CommandException("Invalid request");
        }
        int id = Integer.parseInt(stringId);

        userService.banUser(id);

        return new RedirectResult(CommandName.USERS_PAGE);
    }
}
