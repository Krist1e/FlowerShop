package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.beans.Item;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UpdateQuantityCommand implements Command {

    private final ShoppingCartService shoppingCartService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);
        int itemId = getItemId(request);
        int quantity = getQuantity(request, userId, itemId);

        try {
            shoppingCartService.updateItemQuantity(userId, itemId, quantity);
        } catch (ServiceException e) {
            log.error("Failed to update quantity", e);
            throw new CommandException("Failed to update quantity", e);
        }

        return new RedirectResult(CommandName.SHOPPING_CART_PAGE);
    }

    private int getQuantity(HttpServletRequest request, int userId, int itemId) throws CommandException {
        String stringQuantity = request.getParameter("quantity");
        int quantity;
        if (stringQuantity == null) {
            quantity = getChangedQuantity(request, userId, itemId);
        }
        else {
            quantity = Integer.parseInt(stringQuantity);
        }
        return quantity;
    }

    private int getChangedQuantity(HttpServletRequest request, int userId, int itemId) throws CommandException {
        int changed = getChanged(request);

        Optional<Item> itemOptional;
        try {
            itemOptional = shoppingCartService.getItemById(userId, itemId);
        } catch (ServiceException e) {
            log.error("Failed to get item", e);
            throw new CommandException("Failed to get item", e);
        }
        if (itemOptional.isEmpty()) {
            throw new CommandException("No such item");
        }
        Item item = itemOptional.get();
        return item.getQuantity() + changed;
    }

    private static int getChanged(HttpServletRequest request) throws CommandException {
        String stringChanged = request.getParameter("changed");
        if (stringChanged == null) {
            throw new CommandException("No quantity or changed");
        }
        return Integer.parseInt(stringChanged);
    }

    private static int getItemId(HttpServletRequest request) throws CommandException {
        String stringItemId = request.getParameter("id");
        if (stringItemId == null) {
            throw new CommandException("No item id");
        }
        return Integer.parseInt(stringItemId);
    }
}
