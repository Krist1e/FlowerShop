package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class ViewResult implements CommandResult {

    private final String page;

    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            throw new CommandException("View result failed", e);
        }
    }
}
