package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RedirectResult implements CommandResult {

    private final CommandName command;
    private int pageNumber;

    public RedirectResult(CommandName command, int pageNumber) {
        this.command = command;
        this.pageNumber = pageNumber;
    }

    public RedirectResult(CommandName command) {
        this.command = command;
    }

    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            response.sendRedirect("/controller/" + command.getName() + (pageNumber != 0 ? "?page=" + pageNumber : ""));
        } catch (IOException e) {
            throw new CommandException("Redirect result failed", e);
        }
    }
}
