package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.OrderService;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PlaceOrderCommand implements Command {
    private final OrderService orderService;
    private final ShoppingCartService cartService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String comments = request.getParameter("comments");
        if (address == null || phone == null || comments == null)
            throw new CommandException("Invalid request");

        log.debug("User with id {} placed an order", userId);
        Optional<ShoppingCart> cartOptional;
        try {
            cartOptional = cartService.getCartByUserId(userId);
        } catch (Exception e) {
            log.error("Failed to get cart", e);
            throw new CommandException("Failed to get cart", e);
        }
        if (cartOptional.isEmpty()) {
            log.error("User doesn't have a cart");
            throw new CommandException("User doesn't have a cart");
        }

        ShoppingCart cart = cartOptional.get();
        try {
            orderService.placeOrder(userId, address, phone, comments, cart);
        } catch (ServiceException e) {
            log.error("Failed to place order", e);
            throw new CommandException("Failed to place order", e);
        }
        try {
            cartService.clearCart(userId);
        } catch (ServiceException e) {
            log.error("Failed to clear cart", e);
            throw new CommandException("Failed to clear cart", e);
        }

        log.debug("Order placed");
        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
