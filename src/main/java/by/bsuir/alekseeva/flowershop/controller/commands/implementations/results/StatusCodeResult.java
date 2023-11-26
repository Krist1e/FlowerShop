package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class StatusCodeResult implements CommandResult {
    private final int statusCode;
    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            response.sendError(statusCode);
        } catch (IOException e) {
            throw new CommandException("Status code result failed", e);
        }
    }
}
