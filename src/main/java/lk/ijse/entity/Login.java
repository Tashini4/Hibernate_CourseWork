package lk.ijse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class Login {
    @Id
    private String login;
    private String userID;
    private Date date;
}
