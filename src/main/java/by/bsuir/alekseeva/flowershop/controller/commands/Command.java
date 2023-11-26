package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    CommandResult execute(HttpServletRequest request) throws CommandException;
}