package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class ViewResult implements CommandResult {

    private final String page;

    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/" + page + ".jsp").forward(request, response);
    }
}
