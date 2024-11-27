package lk.ijse.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dto.CourseDTO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.Student_CourseDTO;

import java.sql.SQLException;
import java.util.List;

public class PaymentFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<PaymentDTO> tblPayment;

    @FXML
    private TableColumn<?, ?> colPayID;

    @FXML
    private TableColumn<PaymentDTO, String> colStudent_courseID;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private Button btnBack;
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.PAYMENT);

    public void initialize(){
        loadAllPayment();
        setCellValueFactory();
    }

    private void loadAllPayment() {
        ObservableList<PaymentDTO> obList = FXCollections.observableArrayList();
        try {
            List<PaymentDTO> joinList = paymentBO.getAll();

            if (joinList != null) {
                for (PaymentDTO LIST : joinList) {
                    PaymentDTO tm = new PaymentDTO(
                            LIST.getPay_id(),
                            LIST.getPay_date(),
                            LIST.getPay_amount(),
                            new Student_CourseDTO(
                                    LIST.getStudentCourse().getStudent_course_id(),
                                    new StudentDTO(),
                                    new CourseDTO(),
                                    LIST.getStudentCourse().getRegistration_date()
                            )

                    );
                    obList.add(tm);
                }
            } else {
                System.out.println("No data returned from getAll() method.");
            }
            tblPayment.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPayID.setCellValueFactory(new PropertyValueFactory<>("pay_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("pay_amount"));
        colStudent_courseID.setCellValueFactory(cellData -> {
            PaymentDTO PS = cellData.getValue();
            return new SimpleStringProperty(
                    PS.getStudentCourse() != null ? PS.getStudentCourse().getStudent_course_id() : "N/A"
            );
        });
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
}


