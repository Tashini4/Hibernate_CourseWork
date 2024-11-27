package lk.ijse.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.*;
import lk.ijse.bo.custom.impl.StudentRegisterBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dto.*;
import lk.ijse.entity.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class StudentRegisterFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<Student_CourseDTO> tblStudentCourse;

    @FXML
    private TableColumn<Student_CourseDTO,String> colStudentId;

    @FXML
    private TableColumn<Student_CourseDTO,String> colCourseId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colStudentCourseId;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblStudentName1;

    @FXML
    private ComboBox<String> cmbStudentPhoneNumber;

    @FXML
    private ComboBox<String> cmbCourseName;

    @FXML
    private Label lblCourseID;

    @FXML
    private Label lblStudentID;

    @FXML
    private Label lblFee1;

    @FXML
    private Label lblPaymentId1;

    @FXML
    private Label lblDate1;

    @FXML
    private Label lblStudentCourseId1;

    @FXML
    private Label lblDuration;
    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.STUDENT);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PAYMENT);
    Student_CourseBO studentCourseBO = (Student_CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.STUDENT_COURSE);
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllData();
        setCellValueFactory();
        getCourseIds();
        getStudentIds();
        generateNextId();
        LocalDate();
       lastLoginID();

        tblStudentCourse.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    Payment payment = paymentBO.searchById(newSelection.getStudent_course_id());

                    if (payment != null) {
                        lblPaymentId1.setText(payment.getPay_id());
                    } else {

                        lblPaymentId1.setText("No Payment Found");
                    }

                    lblCourseID.setText(newSelection.getCourse().getCourse_id());
                    lblStudentCourseId1.setText(newSelection.getStudent_course_id());
                    lblStudentID.setText(newSelection.getStudent().getStu_id());
                    cmbCourseName.setValue(newSelection.getCourse().getCourse_name());
                    cmbStudentPhoneNumber.setValue(newSelection.getStudent().getStu_phone());

                } catch (SQLException e) {
                    throw new RuntimeException("Database Error: " + e.getMessage(), e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Class Not Found: " + e.getMessage(), e);
                }
            }
        });
    }

    private void lastLoginID() throws SQLException, ClassNotFoundException {
        Login login =loginDAO.getLastLogin();
        UserID(login.getUserID());
    }

    private void UserID(String ID) throws SQLException, ClassNotFoundException {
        String UserID = ID;
        User user = userBO.searchByIdUser(UserID);
        String position = user.getPosition();

        if (position.equals("Admin")) {
            btnBack.setDisable(false);
            btnClear.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

        } else if (position.equals("Admissions Coordinator")) {
            btnAdd.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnBack.setDisable(false);
            btnClear.setDisable(false);
        }
    }

    private void LocalDate() {
        Date date = Date.valueOf(LocalDate.now());
        lblDate1.setText(String.valueOf(date));
    }

    private void generateNextId() throws SQLException, ClassNotFoundException {
        String PayID = paymentBO.generateNextId();
        lblPaymentId1.setText(PayID);

        String Student_course = studentCourseBO.generateNextId();
        lblStudentCourseId1.setText(Student_course);
    }


    private void getStudentIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> SID = studentBO.getIds();

            for (String s : SID) {
                obList.add(s);
            }
            cmbStudentPhoneNumber.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getCourseIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> CID = courseBO.getIds();

            for (String s : CID) {
                obList.add(s);
            }
            cmbCourseName.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colStudentCourseId.setCellValueFactory(new PropertyValueFactory<>("student_course_id"));
        colStudentId.setCellValueFactory(cellData -> {
            Student_CourseDTO sc = cellData.getValue();
            return new SimpleStringProperty(
                    sc.getStudent() != null ? sc.getStudent().getStu_id() : "N/A"
            );
        });
        colCourseId.setCellValueFactory(cellData -> {
            Student_CourseDTO sc = cellData.getValue();
            return new SimpleStringProperty(
                    sc.getCourse() != null ? sc.getCourse().getCourse_name() : "N/A"
            );
        });
        colDate.setCellValueFactory(new PropertyValueFactory<>("registration_date"));
    }


    private void loadAllData() {
        ObservableList<Student_CourseDTO> obList = FXCollections.observableArrayList();
        try {
            List<Student_CourseDTO> joinList = studentCourseBO.getAll();

            if (joinList != null) {
                for (Student_CourseDTO SC_LIST : joinList) {
                    Student_CourseDTO tm = new Student_CourseDTO(
                            SC_LIST.getStudent_course_id(),
                            SC_LIST.getStudent(),
                            SC_LIST.getCourse(),
                            SC_LIST.getRegistration_date()
                    );
                    obList.add(tm);
                }
            } else {
                System.out.println("No data returned from getAll() method.");
            }

            tblStudentCourse.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String contact = cmbStudentPhoneNumber.getValue();
        String CourseID = lblCourseID.getText();


        Student studentDTO = studentBO.searchByContact(contact);
        if (studentDTO == null) {
            new Alert(Alert.AlertType.WARNING, "Student not found!").show();
            return;
        }
        StudentDTO student = new StudentDTO(
                studentDTO.getStu_id(),
                studentDTO.getStu_name(),
                studentDTO.getStu_phone(),
                studentDTO.getStu_email(),
                studentDTO.getStu_address(),
                new UserDTO()
        );


        Course courseDTO = courseBO.searchById(CourseID);
        if (courseDTO == null) {
            new Alert(Alert.AlertType.WARNING, "Course not found!").show();
            return;
        }
        CourseDTO course = new CourseDTO(
                courseDTO.getCourse_id(),
                courseDTO.getCourse_name(),
                courseDTO.getDuration(),
                courseDTO.getCourse_fee()
        );

        String Student_courseID = lblStudentCourseId1.getText();
        String PaymentID = lblPaymentId1.getText();
        double Fee = Double.parseDouble(lblFee1.getText());
        Date date = Date.valueOf(lblDate1.getText());

        Student_CourseDTO studentCourseDTO = new Student_CourseDTO(Student_courseID, student, course, date);
        PaymentDTO paymentDTO = new PaymentDTO(PaymentID, date, Fee, studentCourseDTO);

        StudentRegisterPlaceDTO studentRegisterPlaceDTO = new StudentRegisterPlaceDTO(studentCourseDTO, paymentDTO);

        boolean isRegister = StudentRegisterBOImpl.StudentRegisterPlace(studentRegisterPlaceDTO);
        if (isRegister) {
            loadAllData();
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Registered").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Registration Unsuccessful!").show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws SQLException, ClassNotFoundException {
        cmbStudentPhoneNumber.getSelectionModel().clearSelection();
        cmbCourseName.getSelectionModel().clearSelection();
        lblCourseID.setText("");
        lblFee1.setText("");
        lblStudentID.setText("");
        lblDuration.setText("");
        generateNextId();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String S_id = lblStudentCourseId1.getText();
        try {
            boolean isDeleted = studentCourseBO.delete(S_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "deleted successfully!").show();
                clearFields();
                loadAllData();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String contact = cmbStudentPhoneNumber.getValue();
        String CourseID = lblCourseID.getText();


        Student studentDTO = studentBO.searchByContact(contact);
        if (studentDTO == null) {
            new Alert(Alert.AlertType.WARNING, "Student not found!").show();
            return;
        }
        StudentDTO student = new StudentDTO(
                studentDTO.getStu_id(),
                studentDTO.getStu_name(),
                studentDTO.getStu_phone(),
                studentDTO.getStu_email(),
                studentDTO.getStu_address(),
                new UserDTO()
        );


        Course courseDTO = courseBO.searchById(CourseID);
        if (courseDTO == null) {
            new Alert(Alert.AlertType.WARNING, "Course not found!").show();
            return;
        }
        CourseDTO course = new CourseDTO(
                courseDTO.getCourse_id(),
                courseDTO.getCourse_name(),
                courseDTO.getDuration(),
                courseDTO.getCourse_fee()
        );

        String Student_courseID = lblStudentCourseId1.getText();
        String PaymentID = lblPaymentId1.getText();
        double Fee = Double.parseDouble(lblFee1.getText());
        Date date = Date.valueOf(lblDate1.getText());

        Student_CourseDTO studentCourseDTO = new Student_CourseDTO(Student_courseID, student, course, date);
        PaymentDTO paymentDTO = new PaymentDTO(PaymentID, date, Fee, studentCourseDTO);

/*
        StudentRegisterPlaceDTO studentRegisterPlaceDTO = new StudentRegisterPlaceDTO(studentCourseDTO, paymentDTO);
*/

        boolean isUpdate = paymentBO.update(paymentDTO);
        boolean isUpdate_SC = studentCourseBO.update(studentCourseDTO);
        if (isUpdate && isUpdate_SC) {
            clearFields();
            loadAllData();
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Update").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Unsuccessful! Update").show();
        }
    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {
        String CourseName = cmbCourseName.getValue();
        try {
            Course course = courseBO.searchByName(CourseName);
            if (course != null) {
                lblCourseID.setText(course.getCourse_id());
                lblFee1.setText(String.valueOf(course.getCourse_fee()));
                lblDuration.setText(course.getDuration());

            } else {
                lblCourseID.setText("Not Found ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStudentOnAction(ActionEvent event) {
        String StudentContact = cmbStudentPhoneNumber.getValue();
        try {
            Student student = studentBO.searchByContact(StudentContact);
            if (student != null) {
                lblStudentID.setText(student.getStu_id());
                lblStudentName1.setText(student.getStu_name());

            } else {
                lblStudentName1.setText("Not Found ");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
