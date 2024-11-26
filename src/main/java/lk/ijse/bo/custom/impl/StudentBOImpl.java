package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);
    @Override
    public boolean save(StudentDTO dto) throws Exception {

        Student student = new Student(
                dto.getStudentId(),
                dto.getStudentName(),
                dto.getStudentPhoneNumber(),
                dto.getStudentEmail(),
                dto.getStudentAddress(),
                new ArrayList<>(),
                new User(dto.getUser().getUser_id(), dto.getUser().getUsername(), dto.getUser().getAddress(),
                        dto.getUser().getUser_phone(), dto.getUser().getUser_email(), dto.getUser().getPosition(),
                        dto.getUser().getPassword()));

        return studentDAO.save(student);
    }
    @Override
    public boolean update(StudentDTO dto) throws Exception {
        Student student = new Student(
                dto.getStudentId(),
                dto.getStudentName(),
                dto.getStudentPhoneNumber(),
                dto.getStudentEmail(),
                dto.getStudentAddress(),
                new ArrayList<>(),
                new User(dto.getUser().getUser_id(), dto.getUser().getUsername(), dto.getUser().getAddress(),
                        dto.getUser().getUser_phone(), dto.getUser().getUser_email(), dto.getUser().getPosition(),
                        dto.getUser().getPassword()));

        return studentDAO.update(student);
    }
    @Override
    public boolean delete(String ID) throws Exception {
        return studentDAO.delete(ID);
    }
    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return studentDAO.generateNextId();
    }
    @Override
    public List<StudentDTO> getAll() throws SQLException, ClassNotFoundException {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> dtoList = new ArrayList<>();
        for (Student student : students) {
            StudentDTO dto = new StudentDTO(
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getStudentPhoneNumber(),
                    student.getStudentEmail(),
                    student.getStudentAddress(),
                    new UserDTO(student.getUser().getUserId(), student.getUser().getUserName(), student.getUser().getAddress(),
                            student.getUser().getPhoneNumber(), student.getUser().getEmail(), student.getUser().getPosition(),
                            student.getUser().getPassword())
            );
            dtoList.add(dto);
        }
        return dtoList;

    }
    @Override
    public List<String> getIds() {
        return studentDAO.getIds();
    }


    @Override
    public Student searchByContact(String id) throws SQLException, ClassNotFoundException{
        return studentDAO.searchByContact(id);
    }
}
