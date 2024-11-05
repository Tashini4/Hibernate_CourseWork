package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtCourseFee;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private TableView<?> tblCourse;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colCourseFee;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblCourseId;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
            Stage stage = (Stage) rootNode.getScene().getWindow();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Dashboard Form");
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}