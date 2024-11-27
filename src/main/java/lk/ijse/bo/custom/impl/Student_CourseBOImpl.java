package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.Student_CourseBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.Student_CourseDAO;
import lk.ijse.dto.CourseDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.Student_CourseDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Course;
import lk.ijse.entity.Student;
import lk.ijse.entity.Student_Course;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student_CourseBOImpl implements Student_CourseBO {
    Student_CourseDAO studentCourseDAO = (Student_CourseDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT_COURSE);

    @Override
    public boolean save(Student_CourseDTO dto) throws Exception {
        return studentCourseDAO.save(new Student_Course(
                dto.getStudent_course_id(),
                new Student(
                        dto.getStudent().getStu_id(),
                        dto.getStudent().getStu_name(),
                        dto.getStudent().getStu_phone(),
                        dto.getStudent().getStu_email(),
                        dto.getStudent().getStu_address(),
                        new ArrayList<>(),
                        new User()
                ),
                new Course(
                        dto.getCourse().getCourse_id(),
                        dto.getCourse().getCourse_name(),
                        dto.getCourse().getDuration(),
                        dto.getCourse().getCourse_fee(),
                        new ArrayList<>()
                ),
                dto.getRegistration_date(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean update(Student_CourseDTO dto) throws Exception {
        return studentCourseDAO.update(new Student_Course(
                dto.getStudent_course_id(),
                new Student(
                        dto.getStudent().getStu_id(),
                        dto.getStudent().getStu_name(),
                        dto.getStudent().getStu_phone(),
                        dto.getStudent().getStu_email(),
                        dto.getStudent().getStu_address(),
                        new ArrayList<>(),
                        new User()
                ),
                new Course(
                        dto.getCourse().getCourse_id(),
                        dto.getCourse().getCourse_name(),
                        dto.getCourse().getDuration(),
                        dto.getCourse().getCourse_fee(),
                        new ArrayList<>()
                ),
                dto.getRegistration_date(),
                new ArrayList<>()
        ));    }

    @Override
    public boolean delete(String ID) throws Exception {
        return studentCourseDAO.delete(ID);
    }
    @Override
    public List<Student_CourseDTO> getAll() throws SQLException, ClassNotFoundException {
        List<Student_Course> SC = studentCourseDAO.getAll();
        List<Student_CourseDTO> dtoList = new ArrayList<>();
        for (Student_Course student_course : SC) {
            Student_CourseDTO dto = new Student_CourseDTO(
                    student_course.getStudent_course_id(),
                    new StudentDTO(
                            student_course.getStudent().getStu_id(),
                            student_course.getStudent().getStu_name(),
                            student_course.getStudent().getStu_phone(),
                            student_course.getStudent().getStu_email(),
                            student_course.getStudent().getStu_address(),
                            new UserDTO()),
                    new CourseDTO(
                            student_course.getCourse().getCourse_id(),
                            student_course.getCourse().getCourse_name(),
                            student_course.getCourse().getDuration(),
                            student_course.getCourse().getCourse_fee()
                    ),
                    student_course.getRegistration_date()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return studentCourseDAO.generateNextId();
    }
}
