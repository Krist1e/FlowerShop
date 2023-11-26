package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CommandResult {
    void executeResult(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
