package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.OrderCart;
import dto.OrderDetails;
import dto.Orders;
import dto.Products;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.ServiceFactory;
import service.SuperService;
import service.custom.OrderService;
import service.custom.ProductService;
import service.custom.UserService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EmpOrderManagement implements Initializable {

    @FXML
    public GridPane gridPaneInsideScrollPane;

    @FXML
    public JFXButton placeorder;

    @FXML
    private JFXComboBox<Integer> comboBoxEmployeeID;

    @FXML
    private JFXComboBox<String> comboBoxPaymentType;

    @FXML
    private JFXComboBox<String> comboBoxProductID;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private JFXTextField lblOrderID;

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

    private int rowCount = 0;
    private double overallTotal = 0.0;
    ObservableList<OrderCart> orderCartList = FXCollections.observableArrayList();

    @FXML
    void btnOnActionAddToCart(ActionEvent event) {
        OrderCart orderCart = new OrderCart(
                comboBoxProductID.getValue(),
                txtName.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Double.parseDouble(txtQty.getText()),
                (Double.parseDouble(txtQty.getText())*Double.parseDouble(txtUnitPrice.getText())),
                Double.parseDouble(txtDiscount.getText())

        );

        orderCartList.add(orderCart);

        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setStyle("-fx-background-color: #f2f2f2; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 12px;");



        VBox productIDVBox = createLabelValueVBox("Product ID", orderCart.getProductID());
        VBox productNameVBox = createLabelValueVBox("Product", orderCart.getName());
        VBox unitPriceVBox = createLabelValueVBox("Unit Price", "$" + String.format("%.2f", orderCart.getUnitPrice()));
        VBox quantityVBox = createLabelValueVBox("Quantity", String.valueOf(orderCart.getQty()));
        VBox totalPriceVBox = createLabelValueVBox("Total Price", "$" + String.format("%.2f", orderCart.getTotal()));
        VBox discountVBox = createLabelValueVBox("Discount", orderCart.getDiscount() + "%");



        Button lastButton = new Button("Del");
        lastButton.setStyle("-fx-font-size: 12px;");
        lastButton.setOnAction(events -> {

            gridPaneInsideScrollPane.getChildren().remove(hBox);
            orderCartList.remove(orderCart);
            rowCount--;

            overallTotal -= orderCart.getTotal();
            updateTotalLabel();
        });


        VBox lastButtonVBox = new VBox(lastButton);
        lastButtonVBox.setStyle("-fx-alignment: center;");


        hBox.getChildren()
                .addAll(productIDVBox, productNameVBox, unitPriceVBox, quantityVBox, totalPriceVBox, discountVBox,lastButtonVBox);

        gridPaneInsideScrollPane.add(hBox, 0, rowCount);

        rowCount++;
        overallTotal += orderCart.getTotal();
        updateTotalLabel();
        scrollPaneForCartProducts.setContent(gridPaneInsideScrollPane);

    }
    private void updateTotalLabel() {
        lblNetTotal.setText(String.format("%.2f", overallTotal));
    }

    private VBox createLabelValueVBox(String labelText, String valueText) {
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setStyle("-fx-padding: 5px;");


        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");


        Label value = new Label(valueText);
        value.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");


        vBox.getChildren().addAll(label, value);
        return vBox;
    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderCartList.forEach(obj->{
            OrderDetails orderDetails = new OrderDetails(
                    lblOrderID.getText(),
                    obj.getProductID(),
                    obj.getQty()
            );
            orderDetailsList.add(orderDetails);
        });
        Orders orders = new Orders(
                lblOrderID.getText(),
                comboBoxEmployeeID.getValue(),
                comboBoxPaymentType.getValue(),
                Double.parseDouble(lblNetTotal.getText()),
                LocalTime.now(),
                LocalDate.now(),
                orderDetailsList
        );
        Integer value = comboBoxEmployeeID.getValue();
        String text = lblOrderID.getText();
        OrderService service = ServiceFactory.getInstance().getService(ServiceType.ORDER);
        service.placeOrder(orders,value);

        Stage primaryStage = (Stage) placeorder.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/orderBill.fxml"));
            Parent root = loader.load();

            OrderBillController orderBillController = loader.getController();
            orderBillController.setOrderID(lblOrderID.getText(),orders,orderDetailsList);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnOnActionProductsView(ActionEvent event) {
        Stage primaryStage = (Stage) placeorder.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/empViewProduct.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserIds();
        loadProductIds();
        loadPaymentMehod();
        localDateAndTime();

        comboBoxProductID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            if(newValue!=null){
                searchProduct(newValue);
            }

        });


    }

    private void searchProduct(String newValue) {
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        Products productById = service.findProductById(newValue);
        txtName.setText(productById.getName());
        txtQtyOnHand.setText(String.valueOf(productById.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(productById.getUnitPrice()));
        txtProductID.setText(productById.getProductID());

    }

    private void loadPaymentMehod() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Cash","Credit Card","Debit Card");
        comboBoxPaymentType.setItems(list);

    }

    private void loadProductIds() {
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        ObservableList<String> ids = service.getIds();
        comboBoxProductID.setItems(ids);
    }

    private void loadUserIds() {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        ObservableList<Integer> ids = service.getIds();
        comboBoxEmployeeID.setItems(ids);

    }

    private void localDateAndTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = format.format(date);

        lblDate.setText(dateNow);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
}
