package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class StudentRegisterPlaceDTO {
    private Student_CourseDTO student_course;
    private PaymentDTO student_payment;
}
