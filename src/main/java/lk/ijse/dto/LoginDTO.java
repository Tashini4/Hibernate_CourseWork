package lk.ijse.dto;

import lombok.*;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class LoginDTO {
    private String login;
    private String UserID;
    private Date date;
}
