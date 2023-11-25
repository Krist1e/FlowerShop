package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.controller.commands.implementations.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final CommandFactory commandFactory = new CommandFactory();
    private final Map<String, Command> commands = new HashMap<>();
    private CommandFactory() {
        commands.put(CommandName.SIGN_IN.getName(), new SignInCommand());
        commands.put(CommandName.SIGN_UP.getName(), new SignUpCommand());
        commands.put(CommandName.SIGN_IN_PAGE.getName(), new SignInPageCommand());
        commands.put(CommandName.SIGN_UP_PAGE.getName(), new SignUpPageCommand());
        commands.put(CommandName.CATALOG_PAGE.getName(), new CatalogPageCommand());
        commands.put(CommandName.SHOPPING_CART_PAGE.getName(), new ShoppingCartPageCommand());
        commands.put(CommandName.ORDER_PAGE.getName(), new OrderPageCommand());
    }

    public Command getCommand(String name) {
        Command command;
        try {
            command = commands.get(name);
        } catch (IllegalArgumentException | NullPointerException e){
            command = commands.get(CommandName.WRONG_REQUEST.getName());
        }
        return command;
    }

    public static CommandFactory getInstance() { return commandFactory; }

}