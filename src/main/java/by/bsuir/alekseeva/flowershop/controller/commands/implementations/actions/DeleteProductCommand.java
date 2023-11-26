package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DeleteProductCommand implements Command {
    private final ProductService productService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String stringProductId = request.getParameter("id");
        if (stringProductId == null)
            throw new CommandException("Failed to get parameters");
        log.debug("Delete product: id = {}", stringProductId);

        int productId = Integer.parseInt(stringProductId);

        try {
            productService.deleteProduct(productId);
        } catch (ServiceException e) {
            log.error("Failed to delete product", e);
            throw new CommandException("Failed to delete product", e);
        }

        log.info("Product {} deleted", productId);
        return new RedirectResult(CommandName.CATALOG_PAGE, pageNumber);
    }
}
