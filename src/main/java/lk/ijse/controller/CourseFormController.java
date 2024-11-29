package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CourseBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dto.CourseDTO;
import lk.ijse.entity.Login;
import lk.ijse.entity.User;
import lk.ijse.util.Regex.Regex;

import java.sql.SQLException;
import java.util.List;

public class CourseFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtProgramName;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtCourseFee;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<CourseDTO> tblCourse;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colProgramme;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblCourseID;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);
    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.COURSE);
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllCourse();
        generateNextId();
        lastLoginID();

        tblCourse.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                lblCourseID.setText(newSelection.getCourse_id());
                txtProgramName.setText(newSelection.getCourse_name());
                txtDuration.setText(newSelection.getDuration());
                txtCourseFee.setText(String.valueOf(newSelection.getCourse_fee()));
            }
        });
    }

    private void generateNextId() throws SQLException, ClassNotFoundException {
        String code = courseBO.generateNextId();
        lblCourseID.setText(code);
    }

    private void loadAllCourse() {
        ObservableList<CourseDTO> obList = FXCollections.observableArrayList();
        try {
            List<CourseDTO> courseList = courseBO.getAll();
            for (CourseDTO courseDTO : courseList) {
                CourseDTO tm = new CourseDTO(
                        courseDTO.getCourse_id(),
                        courseDTO.getCourse_name(),
                        courseDTO.getDuration(),
                        courseDTO.getCourse_fee()
                );

                obList.add(tm);
            }
            tblCourse.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        colProgramme.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("course_fee"));
    }

    private void lastLoginID() throws SQLException, ClassNotFoundException {
        Login login = loginDAO.getLastLogin();
        UserID(login.getUserID());
    }

    private void UserID(String ID) throws SQLException, ClassNotFoundException {
        String UserID = ID;
        User user = userBO.searchByIdUser(UserID);
        String position = user.getPosition();

        if (position.equals("Admin")){
            btnAdd.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnBack.setDisable(false);
            btnClear.setDisable(false);
        }else if(position.equals("Admissions Coordinator")){
            btnBack.setDisable(false);
            btnClear.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String C_ID = lblCourseID.getText();
            String C_NAME = txtProgramName.getText();
            String C_Duration = txtDuration.getText();
            double C_FEE = Double.parseDouble(txtCourseFee.getText());

            CourseDTO courseDTO = new CourseDTO(C_ID,C_NAME,C_Duration,C_FEE);

            if (isValied()){
                boolean isSave = courseBO.save(courseDTO);

                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Course saved successfully!").show();
                    clearFields();
                    loadAllCourse();
                }
            }
            else{
                new Alert(Alert.AlertType.ERROR, "Course not saved successfully!").show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboardForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
    }

    private void clearFields() throws SQLException, ClassNotFoundException {
        generateNextId();
        txtCourseFee.setText("");
        txtDuration.setText("");
        txtProgramName.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCourseID.getText();

        try {
            boolean isDeleted = courseBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Course deleted successfully!").show();
                clearFields();
                loadAllCourse();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Course!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            String C_ID = lblCourseID.getText();
            String C_NAME = txtProgramName.getText();
            String C_Duration = txtDuration.getText();
            double C_FEE = Double.parseDouble(txtCourseFee.getText());

            CourseDTO courseDTO = new CourseDTO(C_ID,C_NAME,C_Duration,C_FEE);

            if (isValied()){boolean isSave = courseBO.update(courseDTO);

                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Course Update successfully!").show();
                    clearFields();
                    loadAllCourse();
                }
            }
            else{
                new Alert(Alert.AlertType.ERROR, "Course not Update successfully!").show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.NAME,txtProgramName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.NUMBER,txtCourseFee)) return false;

        return true;
    }
    @FXML
    void Course_Fee(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.NUMBER,txtCourseFee);
    }

    @FXML
    void Duration(KeyEvent event) {

    }

    @FXML
    void Programme_Name(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.NAME,txtProgramName);
    }

}