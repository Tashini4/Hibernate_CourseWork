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
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Login;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordEncrypt;
import lk.ijse.util.PasswordVerifier;
import lk.ijse.util.Regex.Regex;

import java.sql.SQLException;
import java.util.List;

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
    private ChoiceBox<String> cmbPosition;

    @FXML
    private TableView<UserDTO> tblUsers;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnBack;

    @FXML
    private TextField UserID;
    UserBO userBO = (UserBOImpl) BOFactory.getBoFactory().getBO(BOFactory.BOType.USER);
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllUser();
        generateNextUserId();
        lastLoginID();
        setTableAction();
        setPosition();
    }


    private void setPosition() {
        ObservableList<String> position = FXCollections.observableArrayList();

        position.add("Admin");
        position.add("Admissions Coordinator");

        cmbPosition.setItems(position);
    }

    private void setTableAction() {
        tblUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            UserID.setText(newSelection.getUser_id());
            txtName.setText(newSelection.getUsername());
            txtAddress.setText(newSelection.getAddress());
            txtPhone.setText(newSelection.getUser_phone());
            txtEmail.setText(newSelection.getUser_email());
            cmbPosition.setValue(newSelection.getPosition());
            txtPassword.setText(newSelection.getPassword());

        });
    }

    private void lastLoginID() throws SQLException, ClassNotFoundException {
        Login login = loginDAO.getLastLogin();
        UserID(login.getUserID());
    }

    private void UserID(String ID) throws SQLException, ClassNotFoundException {
        User user = userBO.searchByIdUser(ID);
        String position = user.getPosition();

        if ("Admin".equals(position)) {
            btnBack.setDisable(false);
            btnClear.setDisable(false);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } else if ("Admissions Coordinator".equals(position)) {
            btnAdd.setDisable(false);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnBack.setDisable(false);
            btnClear.setDisable(false);
        }
    }

    private void generateNextUserId() throws SQLException, ClassNotFoundException {
        String code = userBO.generateNextId();
        UserID.setText(code);
    }

    private void loadAllUser() {
        ObservableList<UserDTO> obList = FXCollections.observableArrayList();
        try {
            List<UserDTO> userDTOList = userBO.getAll();
            for (UserDTO userDTO : userDTOList) {
                UserDTO tm = new UserDTO(
                        userDTO.getUser_id(),
                        userDTO.getUsername(),
                        userDTO.getAddress(),
                        userDTO.getUser_phone(),
                        userDTO.getUser_email(),
                        userDTO.getPosition(),
                        userDTO.getPassword()
                );

                obList.add(tm);
            }

            tblUsers.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("user_phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("user_email"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String id = UserID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String position = String.valueOf(cmbPosition.getValue());
            String password = txtPassword.getText();

            String encryptedPassword = PasswordEncrypt.hashPassword(password);

            if (isValied()) {
                if (PasswordVerifier.verifyPassword(password, encryptedPassword)) {
                    UserDTO userDTO = new UserDTO(id, name, address, phone, email, position, encryptedPassword);


                    boolean isSaved = userBO.save(userDTO);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User saved successfully!").show();
                        clearFields();
                        loadAllUser();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "User not saved successfully!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Password verification failed!").show();

            }

        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
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
        generateNextUserId();
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        cmbPosition.setValue("");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = UserID.getText();

        try {
            boolean isDeleted = userBO.delete(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "User deleted successfully!").show();
                clearFields();
                loadAllUser();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete user!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            String id = UserID.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String position = String.valueOf(cmbPosition.getValue());
            String password = txtPassword.getText();

            String encryptedPassword = PasswordEncrypt.hashPassword(password);
            if (isValied()) {
                if (PasswordVerifier.verifyPassword(password, encryptedPassword)) {
                    UserDTO userDTO = new UserDTO(id, name, address, phone, email, position, encryptedPassword);


                    boolean isUpdate = userBO.update(userDTO);
                    if (isUpdate) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User Update successfully!").show();
                        clearFields();
                        loadAllUser();

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "User not Update successfully!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Password update verification failed!").show();
            }
        } catch (Exception e) {

            new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.NAME, txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.ADDRESS, txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.EMAIL, txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.util.Regex.TextField.CONTACT, txtPhone)) return false;

        return true;
    }
    @FXML
    void Address(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.ADDRESS, txtAddress);

    }

    @FXML
    void Email(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.EMAIL, txtEmail);

    }

    @FXML
    void Name(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.NAME, txtName);
    }

    @FXML
    void Password(KeyEvent event) {

    }
    @FXML
    void phone(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.Regex.TextField.CONTACT, txtPhone);
    }
}