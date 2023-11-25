package by.bsuir.alekseeva.flowershop.controller.commands;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}