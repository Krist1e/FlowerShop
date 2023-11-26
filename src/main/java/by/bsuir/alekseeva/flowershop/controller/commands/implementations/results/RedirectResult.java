package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class RedirectResult implements CommandResult {

    private final CommandName command;

    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            response.sendRedirect("/controller/" + command.getName());
        } catch (IOException e) {
            throw new CommandException("Redirect result failed", e);
        }
    }
}
