package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ShoppingCartPageCommand implements Command {
    private final ShoppingCartService cartService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);

        Optional<ShoppingCart> cart;
        try {
            cart = cartService.getCartByUserId(userId);
        } catch (ServiceException e) {
            log.error("Failed to get cart", e);
            throw new CommandException("Failed to get cart", e);
        }
        if (cart.isEmpty()) {
            return new ViewResult("cart");
        }

        request.setAttribute("cart", cart.get());
        return new ViewResult("cart");
    }
}
