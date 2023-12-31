package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.exception.ServiceException;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UpdateProductCommand implements Command {
    private final ProductService productService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String stringProductId = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String priceString = request.getParameter("price");
        String discountString = request.getParameter("discount");
        validate(stringProductId, name, description, priceString, image, discountString);
        log.debug("Update product: id = {}, name = {}, description = {}, image = {}, price = {}, discount = {}", stringProductId, name, description, image, priceString, discountString);

        int productId = Integer.parseInt(stringProductId);
        float price = Float.parseFloat(priceString);
        float discount = discountString.isBlank() ? 0 : Float.parseFloat(discountString);

        try {
            productService.updateProduct(productId, name, description, price, discount, image);
        } catch (ServiceException e) {
            log.error("Failed to update product", e);
            throw new CommandException("Failed to update product", e);
        }

        log.info("Product {} updated", productId);
        return new RedirectResult(CommandName.CATALOG_PAGE, pageNumber);
    }

    private static void validate(String stringProductId, String name, String description, String priceString, String image, String discountString) throws CommandException {
        if (stringProductId == null || name == null || description == null || priceString == null || image == null || discountString == null) {
            log.error("Failed to get parameters");
            throw new CommandException("Failed to get parameters");
        }
        if (name.isBlank() || description.isBlank() || stringProductId.isBlank() || priceString.isBlank()) {
            log.error("Invalid parameters");
            throw new CommandException("Invalid parameters");
        }
    }
}
