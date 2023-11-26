package by.bsuir.alekseeva.flowershop.utils;

import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class RequestUtil {

    private RequestUtil() {
    }

    public static int getPageNumber(HttpServletRequest request) {
        String page = request.getParameter("page");
        if (page == null)
            return 1;
        return Integer.parseInt(page);
    }

    public static int getUserId(HttpServletRequest request) throws CommandException {
        return getUser(request).getId();
    }

    public static User getUser(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (session == null)
            throw new CommandException("Failed to get session");
        User user = (User) session.getAttribute("user");
        if (user == null)
            throw new CommandException("Failed to get user");
        return user;
    }
}
