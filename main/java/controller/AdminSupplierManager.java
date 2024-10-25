package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.Suppliers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.SuperService;
import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminSupplierManager implements Initializable {

    @FXML
    public JFXButton addbtn;
    @FXML
    private GridPane gridPane;

    @FXML
    private Pane paneForAddUpdate;

    @FXML
    private ScrollPane paneForSuppliers;

    @FXML
    private JFXTextField txtCompanyNameUpdateSupplier;

    @FXML
    private JFXTextField txtEmailUpdateSupplier;

    @FXML
    private JFXTextField txtNameSupplierUpdate;

    @FXML
    private JFXTextField txtSearchtextSupplier;

    @FXML
    private JFXTextField txtSupplierCompany;

    @FXML
    private JFXTextField txtSupplierEmail;

    @FXML
    private JFXTextField txtSupplierIDUpdate;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private VBox vBoxForAddSupplier;

    @FXML
    private VBox vboxForUpdateSupplier;

    @FXML
    void btnONActionUpdateSupplier(ActionEvent event) {

    }

    @FXML
    void btnOnActionAddNewSupplier(ActionEvent event) {
        if (txtSupplierId.getText().isEmpty() ||  txtSupplierName.getText().isEmpty() || txtSupplierCompany.getText().isEmpty() || txtSupplierEmail.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING,"All fields must be filled out").show();
            return;
        }

        Suppliers suppliers = new Suppliers(
                txtSupplierId.getText(),
                txtSupplierName.getText(),
                txtSupplierCompany.getText(),
                txtSupplierEmail.getText()
        );
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        boolean b = service.addSupplier(suppliers);
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    @FXML
    void btnOnActionAddSupplier(ActionEvent event) {
        paneForAddUpdate.setVisible(true);
        paneForAddUpdate.setManaged(true);
        vBoxForAddSupplier.setVisible(true);
        vBoxForAddSupplier.setManaged(true);

    }

    @FXML
    void btnOnActionCancel(ActionEvent event) {

    }

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void btnOnActionProductView(ActionEvent event) {
        Stage primaryStage = (Stage) addbtn.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionSearchSupplier(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdateSupplier(ActionEvent event) {

    }


    @FXML
    void btnOnCancelAddSupplier(ActionEvent actionEvent) {
        paneForAddUpdate.setVisible(false);
        paneForAddUpdate.setManaged(false);
        vBoxForAddSupplier.setVisible(false);
        vBoxForAddSupplier.setManaged(false);
    }
    private void loadSuppliers() {
        gridPane.getChildren().clear();
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        List<Suppliers> suppliersList = service.getAll();

        int row = 0;

        for (Suppliers supplier : suppliersList) {
            HBox hbox = new HBox(15);
            hbox.setPadding(new Insets(10));
            hbox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

            Label idLabel = new Label(supplier.getSupplierID());
            idLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

            Label nameLabel = new Label(supplier.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

            Label companyLabel = new Label(supplier.getCompany());
            companyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

            Label emailLabel = new Label(supplier.getEmail());
            emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 3;");

            deleteButton.setOnAction(event -> {
                boolean isDeleted = deleteSupplier(supplier);
                if (isDeleted) {
                    gridPane.getChildren().remove(hbox);
                    loadSuppliers();
                } else {
                    System.out.println("Deletion failed for supplier: " + supplier.getSupplierID());
                }
            });

            deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color: #c9302c; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 3;"));
            deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 3;"));

            hbox.getChildren().addAll(idLabel, nameLabel, companyLabel, emailLabel, deleteButton);
            gridPane.add(hbox, 0, row++);
        }
    }

    private boolean deleteSupplier(Suppliers supplier) {
        SupplierService service = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);
        return service.removeSupplier(supplier.getSupplierID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSuppliers();
    }

    @FXML
    void btnOnActionReportsView(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) addbtn.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminReports.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionEmployeeView(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) addbtn.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminEmployee.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
