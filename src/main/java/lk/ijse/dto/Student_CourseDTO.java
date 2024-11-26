package lk.ijse.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class Student_CourseDTO {
    private String student_course_id;
    private StudentDTO student;
    private CourseDTO course;
    private Date registration_date;
}
