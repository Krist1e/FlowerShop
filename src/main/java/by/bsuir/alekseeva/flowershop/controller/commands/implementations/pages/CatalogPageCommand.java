package by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages;

import by.bsuir.alekseeva.flowershop.beans.Page;
import by.bsuir.alekseeva.flowershop.beans.Product;
import by.bsuir.alekseeva.flowershop.controller.commands.Command;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandResult;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.results.ViewResult;
import by.bsuir.alekseeva.flowershop.exception.CommandException;
import by.bsuir.alekseeva.flowershop.service.ProductService;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import by.bsuir.alekseeva.flowershop.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;

public class CatalogPageCommand implements Command {

    private static final int PAGE_SIZE = 30;

    private final ProductService productService = ServiceFactory.getInstance().getProductService();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        int page = RequestUtil.getPageNumber(request);
        Page<Product> products = productService.getInStockProducts(page, PAGE_SIZE);

        request.setAttribute("products", products);
        return new ViewResult("catalog");
    }
}
