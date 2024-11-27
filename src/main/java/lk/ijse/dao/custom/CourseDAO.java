package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Course;

import java.sql.SQLException;

public interface CourseDAO extends CrudDAO<Course> {
    Course searchByName(String id) throws SQLException, ClassNotFoundException;
}
