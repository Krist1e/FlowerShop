package by.bsuir.alekseeva.flowershop.beans;

import lombok.*;

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
    private int rating;
}
