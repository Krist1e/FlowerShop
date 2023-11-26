package by.bsuir.alekseeva.flowershop.controller.commands;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CommandResult {
    void executeResult(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
