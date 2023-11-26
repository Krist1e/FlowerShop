package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.UserService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UsersPageCommand implements Command {

    private static final int PAGE_SIZE = 10;

    private final UserService userService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int page = RequestUtil.getPageNumber(request);

        Page<User> users;
        try {
            users = userService.getUsers(page, PAGE_SIZE);
        } catch (ServiceException e) {
            log.error("Failed to get users", e);
            throw new CommandException("Failed to get users", e);
        }

        request.setAttribute("users", users);
        return new ViewResult("users");
    }
}
