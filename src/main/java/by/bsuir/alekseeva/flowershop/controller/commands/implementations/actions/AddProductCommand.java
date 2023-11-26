package by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions;

import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.RedirectResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

public class AddProductCommand implements Command {

    private final ProductService productService = ServiceFactory.getInstance().getProductService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String image = request.getParameter("image");
        String discount = request.getParameter("discount");

        return new RedirectResult(CommandName.CATALOG_PAGE);
    }
}
