package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.UserService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UnbanCommand implements Command {
    private final UserService userService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String stringId = request.getParameter("id");
        if (stringId == null) {
            throw new CommandException("Invalid request");
        }
        int id = Integer.parseInt(stringId);

        try {
            userService.unbanUser(id);
        } catch (ServiceException e) {
            log.error("Failed to unban user", e);
            throw new CommandException("Failed to unban user", e);
        }

        log.debug("User unbanned");
        return new RedirectResult(CommandName.USERS_PAGE, pageNumber);
    }
}
