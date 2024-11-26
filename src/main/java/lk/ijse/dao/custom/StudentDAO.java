package lk.ijse.dao.custom;

import lk.ijse.entity.Student;

import java.sql.SQLException;

public interface StudentDAO extends CourseDAO<Student> {
    public Student searchByContact(String id) throws SQLException, ClassNotFoundException;
}
