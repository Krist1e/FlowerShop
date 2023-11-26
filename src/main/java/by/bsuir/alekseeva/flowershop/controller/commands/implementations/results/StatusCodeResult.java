package by.bsuir.alekseeva.flowershop.controller.commands.implementations.results;

import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class StatusCodeResult implements CommandResult {
    private final int statusCode;
    @Override
    public void executeResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(statusCode);
    }
}
