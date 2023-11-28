package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.Order;
import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProfilePageCommand implements Command {

    private static final int PAGE_SIZE = 5;

    private final OrderService orderService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int page = RequestUtil.getPageNumber(request);
        User user = RequestUtil.getUser(request);

        Page<Order> orders;
        try {
            orders = orderService.getOrdersByUserId(user.getId(), page, PAGE_SIZE);
        } catch (ServiceException e) {
            log.error("Failed to get orders", e);
            throw new CommandException("Failed to get orders", e);
        }

        request.setAttribute("orders", orders);
        request.setAttribute("user", user);
        return new ViewResult("profile");
    }
}
