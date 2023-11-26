package by.bsuir.alekseeva.flowershop.filter;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User user = null;
        try {
            user = RequestUtil.getUser(request);
        } catch (CommandException e) {
            log.debug("User is not logged in");
        }

        Role userRole;
        if (user != null) {
            userRole = user.getRole();
        } else {
            userRole = Role.GUEST;
        }
        log.debug("User role: {}", userRole);

        CommandName command = CommandName.of(request);
        List<Role> commandRoles = SecurityConfig.getCommandRoles(command);
        log.debug("Command {} roles: {}", command, commandRoles);

        if (commandRoles.contains(userRole)) {
            log.debug("User {} has access to command {}", user, command);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.debug("User {} has no access to command {}", user, command);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
