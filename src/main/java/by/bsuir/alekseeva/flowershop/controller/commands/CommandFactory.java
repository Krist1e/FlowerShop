package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions.*;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages.*;
import by.bsuir.alekseeva.flowershop.exception.CommandFactoryException;
import by.bsuir.alekseeva.flowershop.service.ServiceFactory;
import jakarta.servlet.http.HttpServletRequest;

import java.util.EnumMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory commandFactory = new CommandFactory();
    private final Map<CommandName, Command> commands = new EnumMap<>(CommandName.class);

    private CommandFactory() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        commands.put(CommandName.SIGN_IN, new SignInCommand(serviceFactory.getAuthenticationService(), serviceFactory.getUserService()));
        commands.put(CommandName.SIGN_UP, new SignUpCommand(serviceFactory.getAuthenticationService()));
        commands.put(CommandName.SIGN_OUT, new SignOutCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(CommandName.ADD_TO_CART, new AddToCartCommand(serviceFactory.getCartService()));
        commands.put(CommandName.UPDATE_QUANTITY, new UpdateQuantityCommand(serviceFactory.getCartService()));
        commands.put(CommandName.PLACE_ORDER, new PlaceOrderCommand(serviceFactory.getOrderService(), serviceFactory.getCartService()));
        commands.put(CommandName.SIGN_IN_PAGE, new SignInPageCommand());
        commands.put(CommandName.SIGN_UP_PAGE, new SignUpPageCommand());
        commands.put(CommandName.SHOPPING_CART_PAGE, new ShoppingCartPageCommand(serviceFactory.getCartService()));
        commands.put(CommandName.PROFILE_PAGE, new ProfilePageCommand(serviceFactory.getOrderService()));
        commands.put(CommandName.CATALOG_PAGE, new CatalogPageCommand(serviceFactory.getProductService()));
        commands.put(CommandName.ADD_PRODUCT, new AddProductCommand(serviceFactory.getProductService()));
        commands.put(CommandName.DELETE_PRODUCT, new DeleteProductCommand(serviceFactory.getProductService()));
        commands.put(CommandName.UPDATE_PRODUCT, new UpdateProductCommand(serviceFactory.getProductService()));
        commands.put(CommandName.USERS_PAGE, new UsersPageCommand(serviceFactory.getUserService()));
        commands.put(CommandName.BAN_USER, new BanCommand(serviceFactory.getUserService()));
        commands.put(CommandName.UNBAN_USER, new UnbanCommand(serviceFactory.getUserService()));
        commands.put(CommandName.COUPONS_PAGE, new CouponsPageCommand(serviceFactory.getCouponService()));
        commands.put(CommandName.ADD_COUPON, new AddCouponCommand(serviceFactory.getCouponService()));
        commands.put(CommandName.DELETE_COUPON, new DeleteCouponCommand(serviceFactory.getCouponService()));
        commands.put(CommandName.APPLY_COUPON, new ApplyCouponCommand(serviceFactory.getCouponService(), serviceFactory.getCartService()));
    }

    public Command getCommand(HttpServletRequest request) throws CommandFactoryException {
        Command command;
        try {
            CommandName commandName = CommandName.of(request);
            command = commands.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new CommandFactoryException("Invalid command", e);
        }

        return command;
    }

    public static CommandFactory getInstance() {
        return commandFactory;
    }

}