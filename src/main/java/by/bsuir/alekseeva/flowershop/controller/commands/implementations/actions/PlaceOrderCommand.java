package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlaceOrderCommand implements Command {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final ShoppingCartService cartService = ServiceFactory.getInstance().getCartService();
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String comments = request.getParameter("comments");
        if (address == null || phone == null || comments == null)
            throw new CommandException("Invalid request");

        log.debug("User with id {} placed an order", userId);

        ShoppingCart cart = cartService.getCartByUserId(userId).orElseThrow(() -> new CommandException("User doesn't have a cart"));
        orderService.createOrder(userId, cart.getCartItems(), address, phone, comments);
        cartService.clearCart(userId);

        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
