package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminReportController {

    @FXML
    public JFXButton viewRepoprt;

    @FXML
    private JFXTextField comboBoxOrderId;

    @FXML
    private JFXTextField comboBoxProductId;

    @FXML
    private JFXTextField comboBoxReturnOrderId;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblOrderEmplyeeId;

    @FXML
    private Label lblOrderNettrotal;

    @FXML
    private Label lblOrderTime;

    @FXML
    private Label lblOrderdate;

    @FXML
    private Label lblOrdersCount;

    @FXML
    private Label lblReturnEmplyeeId;

    @FXML
    private Label lblReturnNetTotal;

    @FXML
    private Label lblreturnTime;

    @FXML
    private Label lblreturndate;

    @FXML
    private Label lblreturnorderCount;

    @FXML
    private JFXButton reportPrint;

    @FXML
    private Label txtEmployeeNew;

    @FXML
    private Label txtHandOnQty;

    @FXML
    private Label txtLeftOty;

    @FXML
    private Label txtQtySold;

    @FXML
    private Label txtSupplierCount;

    @FXML
    private Label txtSupplierDel;

    @FXML
    private Label txtSupplierNew;

    @FXML
    private Label txtemplyeeDel;

    @FXML
    void BtnOnActionNavigationToReports(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigateToProducts(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigationEmplyee(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigationToSuppliers(ActionEvent event) {

    }

    @FXML
    void btnOnActionPrintReport(ActionEvent event) {

    }

    @FXML
    void btnOnActionView(ActionEvent event) {
        Stage primaryStage = (Stage) viewRepoprt.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminReportView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

}
