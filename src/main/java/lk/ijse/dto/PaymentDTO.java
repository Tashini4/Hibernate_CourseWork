package lk.ijse.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

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
    private String pay_date;
    private String pay_amount;
    private Student_CourseDTO studentCourse;
}
