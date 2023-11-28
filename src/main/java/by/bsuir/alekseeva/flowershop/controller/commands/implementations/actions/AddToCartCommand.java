package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

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

@Slf4j
@RequiredArgsConstructor
public class AddToCartCommand implements Command {

    private final ShoppingCartService cartService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        int userId = RequestUtil.getUserId(request);
        String stringProductId = request.getParameter("product");
        if (stringProductId == null) {
            log.error("Product id is null");
            throw new CommandException("Product id is null");
        }
        int productId = Integer.parseInt(stringProductId);

        try {
            cartService.addItemToCart(userId, productId);
        } catch (ServiceException e) {
            log.error("Failed to add product to cart", e);
            throw new CommandException("Failed to add product to cart", e);
        }

        return new RedirectResult(CommandName.CATALOG_PAGE, pageNumber);
    }
}
