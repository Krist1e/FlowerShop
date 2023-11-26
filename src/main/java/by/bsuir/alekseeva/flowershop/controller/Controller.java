package by.bsuir.alekseeva.flowershop.controller;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandFactory;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    private final CommandFactory commandFactory = CommandFactory.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Request {}", request.getPathInfo());
        try {
            Command command = commandFactory.getCommand(request);
            log.info("Command {}", command);
            CommandResult commandResult = command.execute(request);
            log.info("Command result {}", commandResult);

            commandResult.executeResult(request, response);
        } catch (Exception e) {
            throw new ServletException("Executing command failed.", e);
        }
    }

    public void destroy() {
    }
}