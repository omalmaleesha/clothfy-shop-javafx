package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class EmpOrderManagement {

    @FXML
    private JFXComboBox<?> comboBoxEmployeeID;

    @FXML
    private JFXComboBox<?> comboBoxPaymentType;

    @FXML
    private JFXComboBox<?> comboBoxProductID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblTime;

    @FXML
    private ScrollPane scrollPaneForCartProducts;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtProductID;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private Label txtTotal;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void btnOnActionProductsView(ActionEvent event) {

    }

}
