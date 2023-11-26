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
public class AddProductCommand implements Command {

    private final ProductService productService;

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int pageNumber = RequestUtil.getPageNumber(request);
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        String priceString = request.getParameter("price");
        String discountString = request.getParameter("discount");
        if (name == null || description == null || priceString == null || image == null || discountString == null)
            throw new CommandException("Failed to get parameters");
        log.debug("Add product: name = {}, description = {}, image = {}, price = {}, discount = {}", name, description, image, priceString, discountString);

        float price = Float.parseFloat(priceString);
        float discount = Float.parseFloat(discountString);

        try {
            productService.addProduct(name, description, price, discount, image);
        } catch (ServiceException e) {
            log.error("Failed to add product", e);
            throw new CommandException("Failed to add product", e);
        }

        log.debug("Product added");
        return new RedirectResult(CommandName.CATALOG_PAGE, pageNumber);
    }
}
