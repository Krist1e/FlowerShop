package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignOutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }
        log.debug("User signed out");
        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
