package by.bsuir.alekseeva.flowershop.beans;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    private int id;
    private int code;
    private String name;
    private float discount;
}

