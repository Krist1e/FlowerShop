package by.bsuir.alekseeva.flowershop.controller;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandFactory;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.StatusCodeResult;
import by.bsuir.alekseeva.flowershop.dao.connection.ConnectionPool;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.CommandFactoryException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class Controller extends HttpServlet {

    private final transient CommandFactory commandFactory = CommandFactory.getInstance();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Request {}", request.getPathInfo());
        CommandResult commandResult;
        try {
            Command command = commandFactory.getCommand(request);
            log.debug("Command {}", command.getClass().getSimpleName());
            commandResult = command.execute(request);
        } catch (CommandFactoryException e) {
            log.error("Command not found", e);
            commandResult = new StatusCodeResult(404);
        } catch (CommandException e) {
            log.error("Command failed", e);
            commandResult = new StatusCodeResult(500);
        }

        try {
            log.debug("Command result {}", commandResult.getClass().getSimpleName());
            commandResult.executeResult(request, response);
        } catch (CommandException e) {
            log.error("Command result failed", e.getInnerException());
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.init();
        } catch (Exception e) {
            log.error("Can not init ConnectionPool", e);
            throw new ServletException("Can not init ConnectionPool", e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (SQLException e) {
            log.error("Can not destroy ConnectionPool", e);
        }
    }
}
