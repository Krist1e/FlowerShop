package by.bsuir.alekseeva.flowershop.controller.commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandName {
    SIGN_UP("sign-up"),
    SIGN_IN("sign-in"),
    SIGN_OUT("sign-out"),
    ADD_TO_CART("add-to-cart"),
    UPDATE_QUANTITY("update-quantity"),
    PLACE_ORDER("place-order"),
    CHANGE_LOCALE("change-locale"),
    SIGN_UP_PAGE("sign-up-page"),
    SIGN_IN_PAGE("sign-in-page"),
    CATALOG_PAGE("catalog-page"),
    SHOPPING_CART_PAGE("shopping-cart-page"),
    PROFILE_PAGE("profile-page"),
    USERS_PAGE("users-page"),
    COUPONS_PAGE("coupons-page"),
    BAN_USER("ban"),
    UNBAN_USER("unban"),
    ADD_PRODUCT("add-product"),
    DELETE_PRODUCT("delete-product"),
    UPDATE_PRODUCT("update-product"),
    ADD_COUPON("add-coupon"),
    DELETE_COUPON("delete-coupon"),
    APPLY_COUPON("apply-coupon");

    private final String name;

    public static CommandName of(HttpServletRequest request) {
        String commandName = request.getPathInfo().substring(1);
        return of(commandName);
    }

    public static CommandName of(String name) {
        for (CommandName commandName : CommandName.values()) {
            if (commandName.getName().equals(name)) {
                return commandName;
            }
        }
        throw new IllegalArgumentException("No such command");
    }
}

