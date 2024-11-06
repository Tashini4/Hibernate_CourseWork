package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private String studentId;
    private String studentName;
    private String studentPhoneNumber;
    private String email;
    private String address;

}
