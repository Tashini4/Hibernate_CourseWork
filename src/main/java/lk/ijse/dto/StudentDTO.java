package lk.ijse.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.DatabaseMetaData;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class StudentDTO {
    private String stu_id;
    private String stu_name;
    private String stu_phone;
    private String stu_email;
    private String stu_address;
    private UserDTO user;


   /* public StudentDTO(String sId, String sName, String phone, String email, String address, UserDTO userDTO) {
    }*/
}
