package by.bsuir.alekseeva.flowershop.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private String imagePath;
    private float price;
    private float discount;

    public float getDiscountedPrice() {
        return price * (1 - discount);
    }
}
