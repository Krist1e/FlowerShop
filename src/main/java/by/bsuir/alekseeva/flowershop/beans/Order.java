package by.bsuir.alekseeva.flowershop.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private User user;
    private List<Item> orderItems;
    private float totalPrice;
    private OrderStatus status;
    private LocalDateTime date;
    private String address;
}

