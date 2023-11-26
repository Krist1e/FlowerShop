package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.beans.User;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;

public class OrderCommand implements Command {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final ShoppingCartService cartService = ServiceFactory.getInstance().getCartService();
    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        User user = (User) request.getSession().getAttribute("user");
        ShoppingCart cart = cartService.getCartByUserId(user.getId()).orElseThrow();
        orderService.createOrder(user.getId(), cart.getCartItems(), "address");
        cartService.clearCart(user.getId());
        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
