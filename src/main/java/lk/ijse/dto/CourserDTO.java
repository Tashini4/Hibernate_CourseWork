package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourserDTO {
    private String courseId;
    private String courseName;
    private String duration;
    private String courseFee;
}
