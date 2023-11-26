package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.StatusCodeResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChangeLocaleCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String locale = request.getParameter("locale");
        log.debug("Changing locale to {}", locale);
        request.getSession().setAttribute("locale", locale);
        return new StatusCodeResult(200);
    }
}
