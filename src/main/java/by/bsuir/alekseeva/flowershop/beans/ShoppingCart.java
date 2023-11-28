package by.bsuir.alekseeva.flowershop.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private int id;
    private User user;
    private List<Item> cartItems;
    private Coupon coupon;
    private float totalPrice;

    public float getDiscountedPrice() {
        return totalPrice * (1 - coupon.getDiscount());
    }
}

