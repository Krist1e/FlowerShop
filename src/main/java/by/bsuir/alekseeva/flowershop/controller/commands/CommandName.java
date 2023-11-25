package by.bsuir.alekseeva.flowershop.controller.commands;

public enum CommandName {
    SIGN_UP("sign-up"),
    SIGN_IN("sign-in"),
    SIGN_UP_PAGE("sign-up-page"),
    SIGN_IN_PAGE("sign-in-page"),
    CATALOG_PAGE("catalog-page"),
    SHOPPING_CART_PAGE("shopping-cart-page"),
    ORDER_PAGE("order-page"),
    ADMIN_PAGE("admin-page"),
    WRONG_REQUEST("wrong-request");


    private final String name;

    CommandName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
