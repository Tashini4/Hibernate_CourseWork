package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String courseId;
    private String courseName;
    private String duration;
    private String courseFee;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourses;
}
