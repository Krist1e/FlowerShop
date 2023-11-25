package by.bsuir.alekseeva.flowershop.controller;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Slf4j
@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    private final CommandFactory commandFactory = CommandFactory.getInstance();

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Request {}", request.getPathInfo());
        try {
            String commandName = request.getPathInfo().substring(1);
            Command command = commandFactory.getCommand(commandName);
            String view = command.execute(request, response);

            if (view.equals(commandName)) {
                request.getRequestDispatcher("/WEB-INF/views/" + view + ".jsp").forward(request, response);
            }
        else {
                response.sendRedirect(view);
            }
        }
        catch (Exception e) {
            throw new ServletException("Executing command failed.", e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("Request {}", request.getPathInfo());
        response.setContentType("text/html");
        service(request, response);
    }

    public void destroy() {
    }
}
