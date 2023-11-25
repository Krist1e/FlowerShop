package by.bsuir.alekseeva.flowershop.beans;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private Role role = Role.USER;
}
