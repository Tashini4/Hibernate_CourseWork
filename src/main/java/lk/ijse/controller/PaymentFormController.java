package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class PaymentFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tblPayment;

    @FXML
    private TableColumn<?, ?> colPayID;

    @FXML
    private TableColumn<?, ?> colStudent_courseID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private Button btnBack;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

}
