package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {


    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtUseName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    void btnLoginOnAction(ActionEvent event) {

    }
     /*   String userName = txtUseName.getText();
        String password = txtPassword.getText();

        try {
            Boolean check = userDAO.check(new UserDTO(userName, password));
            if (check) {
                navigationToTheDashboard();
                new Alert(Alert.AlertType.CONFIRMATION, "SuccessFull login").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Sorry! Login can't be find").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void navigationToTheDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboardForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage= (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }
}
    }*/

}
