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
public class BanCommand implements Command {

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
            userService.banUser(id);
        } catch (ServiceException e) {
            log.error("Failed to ban user", e);
            throw new CommandException("Failed to ban user", e);
        }

        log.debug("User banned");
        return new RedirectResult(CommandName.USERS_PAGE, pageNumber);
    }
}
