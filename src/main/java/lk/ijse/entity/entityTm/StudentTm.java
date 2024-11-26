package lk.ijse.entity.entityTm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTm {
    private String studentId;
    private String studentName;
    private String studentPhoneNumber;
    private String studentEmail;
    private String studentAddress;
}
