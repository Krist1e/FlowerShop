package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions.*;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages.*;
import by.bsuir.alekseeva.flowershop.exception.CommandFactoryException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory commandFactory = new CommandFactory();
    private final Map<CommandName, Command> commands = new HashMap<>();

    private CommandFactory() {
        commands.put(CommandName.SIGN_IN, new SignInCommand());
        commands.put(CommandName.SIGN_UP, new SignUpCommand());
        //commands.put(CommandName.LOG_OUT, new LogOutCommand());
        commands.put(CommandName.ADD_TO_CART, new AddToCartCommand());
        commands.put(CommandName.UPDATE_QUANTITY, new UpdateQuantityCommand());
        commands.put(CommandName.PLACE_ORDER, new PlaceOrderCommand());
        commands.put(CommandName.SIGN_IN_PAGE, new SignInPageCommand());
        commands.put(CommandName.SIGN_UP_PAGE, new SignUpPageCommand());
        commands.put(CommandName.CATALOG_PAGE, new CatalogPageCommand());
        commands.put(CommandName.SHOPPING_CART_PAGE, new ShoppingCartPageCommand());
        commands.put(CommandName.PROFILE_PAGE, new ProfilePageCommand());
        commands.put(CommandName.USERS_PAGE, new UsersPageCommand());
        commands.put(CommandName.BAN_USER, new BanCommand());
        commands.put(CommandName.UNBAN_USER, new UnbanCommand());
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