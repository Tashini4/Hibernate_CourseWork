package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private ChoiceBox<?> cmbPosition;

    @FXML
    private TableView<?> tblUser;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colPhoneNumber;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnBack;

    @FXML
    private Label lblUserId;

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
