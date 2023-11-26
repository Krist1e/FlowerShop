package by.bsuir.alekseeva.flowershop.controller.commands;

import by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions.AddToCartCommand;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions.SignInCommand;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.actions.SignUpCommand;
import by.bsuir.alekseeva.flowershop.controller.commands.implementations.pages.*;
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
        commands.put(CommandName.SIGN_IN_PAGE, new SignInPageCommand());
        commands.put(CommandName.SIGN_UP_PAGE, new SignUpPageCommand());
        commands.put(CommandName.CATALOG_PAGE, new CatalogPageCommand());
        commands.put(CommandName.SHOPPING_CART_PAGE, new ShoppingCartPageCommand());
        commands.put(CommandName.ORDER_PAGE, new OrderPageCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        Command command;
        CommandName commandName = CommandName.of(request);
        command = commands.get(commandName);
        return command;
    }

    public static CommandFactory getInstance() { return commandFactory; }

}