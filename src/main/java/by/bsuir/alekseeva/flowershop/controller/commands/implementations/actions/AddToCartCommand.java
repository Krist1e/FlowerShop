package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.service.ShoppingCartService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddToCartCommand implements Command {

    private final ShoppingCartService cartService = ServiceFactory.getInstance().getCartService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int userId = RequestUtil.getUserId(request);
        String stringProductId = request.getParameter("product");
        if (stringProductId == null) {
            log.error("Product id is null");
            throw new CommandException("Product id is null");
        }
        int productId = Integer.parseInt(stringProductId);

        cartService.addItemToCart(userId, productId);

        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
