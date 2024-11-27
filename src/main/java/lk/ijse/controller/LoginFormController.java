package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.LoginDTO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordVerifier;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginFormController {

    public Label lblLogin;
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);
    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.LOGIN);
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    public void initialize() throws SQLException, ClassNotFoundException {
       generateNextID();
    }

    private void generateNextID() throws SQLException, ClassNotFoundException {
        String LoginID = loginBO.generateNextId();
        lblLogin.setText(LoginID);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String ID = lblLogin.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        Date date = Date.valueOf(LocalDate.now());

        try {
            checkCredential(ID, username, password, date, event);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Login Successful!").show();
        }
    }

    private void checkCredential(String ID, String username, String password, Date date, ActionEvent event) throws Exception {
        User user = userDAO.searchByUsername(username);

        if (user != null) {
            if (PasswordVerifier.verifyPassword(password, user.getPassword())) {
                LoginDTO loginDTO = new LoginDTO(ID, user.getUser_id(), date);
                boolean isSave = loginBO.save(loginDTO);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").show();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboardForm.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    stage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Login failed!").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid password. Please try again.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "User not found. Please check your username.").show();
        }
    }
}

