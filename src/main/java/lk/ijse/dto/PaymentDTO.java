package lk.ijse.dto;

import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaymentDTO {
    @Id
    private String pay_id;
    private Date pay_date;
    private double pay_amount;
    private Student_CourseDTO studentCourse;


}
