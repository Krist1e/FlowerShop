package by.bsuir.alekseeva.flowershop.filter;

import by.bsuir.alekseeva.flowershop.beans.Role;
import by.bsuir.alekseeva.flowershop.controller.commands.CommandName;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static by.bsuir.alekseeva.flowershop.controller.commands.CommandName.*;

public class SecurityConfig {
    private static final List<Role> allRoles = List.of(Role.values());

    private static final Map<CommandName, List<Role>> commandRoles = new EnumMap<>(CommandName.class);

    static {
        commandRoles.put(SIGN_UP, allRoles);
        commandRoles.put(SIGN_IN, allRoles);
        commandRoles.put(SIGN_OUT, allRoles);
        commandRoles.put(SIGN_UP_PAGE, allRoles);
        commandRoles.put(SIGN_IN_PAGE, allRoles);
        commandRoles.put(CHANGE_LOCALE, allRoles);
        commandRoles.put(CATALOG_PAGE, List.of(Role.USER, Role.ADMIN, Role.GUEST));
        commandRoles.put(ADD_TO_CART, List.of(Role.USER, Role.ADMIN));
        commandRoles.put(UPDATE_QUANTITY, List.of(Role.USER, Role.ADMIN));
        commandRoles.put(PLACE_ORDER, List.of(Role.USER, Role.ADMIN));
        commandRoles.put(SHOPPING_CART_PAGE, List.of(Role.USER, Role.ADMIN));
        commandRoles.put(PROFILE_PAGE, List.of(Role.USER, Role.ADMIN));
        commandRoles.put(USERS_PAGE, List.of(Role.ADMIN));
        commandRoles.put(COUPONS_PAGE, List.of(Role.ADMIN));
        commandRoles.put(BAN_USER, List.of(Role.ADMIN));
        commandRoles.put(UNBAN_USER, List.of(Role.ADMIN));
        commandRoles.put(ADD_PRODUCT, List.of(Role.ADMIN));
        commandRoles.put(DELETE_PRODUCT, List.of(Role.ADMIN));
        commandRoles.put(UPDATE_PRODUCT, List.of(Role.ADMIN));
        commandRoles.put(ADD_COUPON, List.of(Role.ADMIN));
        commandRoles.put(DELETE_COUPON, List.of(Role.ADMIN));
        commandRoles.put(APPLY_COUPON, List.of(Role.USER, Role.ADMIN));
    }

    public static List<Role> getCommandRoles(CommandName commandName) {
        try {
            return commandRoles.get(commandName);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("No such command");
        }
    }
}
