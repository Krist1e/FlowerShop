package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.beans.ShoppingCart;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public class ShoppingCartPageCommand implements Command {
    private final ShoppingCartService cartService = ServiceFactory.getInstance().getCartService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);

        Optional<ShoppingCart> cart = cartService.getCartByUserId(userId);
        if (cart.isEmpty()) {
            return new ViewResult("shoppingCart");
        }
        List<Item> items = cart.get().getCartItems();

        request.setAttribute("items", items);
        return new ViewResult("shoppingCart");
    }
}
