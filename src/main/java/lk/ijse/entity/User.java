package lk.ijse.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String userName;
    private String address;
    private String phoneNumber;
    private String email;
    private String position;
    private String password;
}
