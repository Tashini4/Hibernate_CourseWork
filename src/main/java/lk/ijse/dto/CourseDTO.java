package lk.ijse.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class CourseDTO {
    @Id
    private String course_id;
    private String course_name;
    private String duration;
    private double course_fee;
}
