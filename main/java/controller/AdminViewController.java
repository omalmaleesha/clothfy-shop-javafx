package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.ProductService;
import util.ServiceType;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    @FXML
    public JFXButton addButton;

    @FXML
    private JFXButton btnOnAddImg;

    @FXML
    private JFXComboBox<String> comboBoxCategory;

    @FXML
    private JFXComboBox<String> comboBoxSize;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private ScrollPane paneForProducts;

    @FXML
    private Pane paneForVboxes;

    @FXML
    private JFXTextField txtProductID;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtSupplierID;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextField txtname;

    @FXML
    private VBox vboxForaddProduct;

    private byte[] imageBytes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> strings = FXCollections.observableArrayList("kids","ladies","gents");
        comboBoxCategory.setItems(strings);
        ObservableList<String> size = FXCollections.observableArrayList("s","m","l");
        comboBoxSize.setItems(size);
        addProductsToGrid();
    }
    private void addProductsToGrid() {
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        List<Products> products = service.getProducts();

        mainGridPane.getChildren().clear();
        int columns = 3;
        int row = 0;

        if(products != null) {
            for (int i = 0; i < products.size(); i++) {
                Products product = products.get(i);

                VBox productBox = new VBox(10);
                productBox.setStyle(
                        "-fx-border-color: black;" +
                                "-fx-border-width: 1;" +
                                "-fx-background-color: #f0f0f0;" +
                                "-fx-border-radius: 10;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 1;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.5, 0, 0);"
                );


                productBox.setAlignment(Pos.CENTER);

                byte[] imageData = product.getImage();

                InputStream inputStream = new ByteArrayInputStream(imageData);
                Image image = new Image(inputStream);

                ImageView imageView = new ImageView();
                imageView.setImage(image);


                imageView.setFitWidth(150);
                imageView.setFitHeight(120);

                Label productName = new Label(product.getName());
                productName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label unitPrice = new Label("$" + product.getUnitPrice());
                unitPrice.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");

                productBox.getChildren().addAll(imageView, productName, unitPrice);

                int column = i % columns;
                row = i / columns;

                mainGridPane.add(productBox, column, row);


            }

            mainGridPane.setVgap(10);
            mainGridPane.setHgap(10);

            paneForProducts.setContent(mainGridPane);
        }else{
            System.out.println("null");
        }
    }


    @FXML
    void btnOnActionAddImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) btnOnAddImg.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            btnOnAddImg.setText("Selected Img");
            try {
                imageBytes = Files.readAllBytes(file.toPath());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnOnActionAddNewProduct(ActionEvent event) {
        Products product = new Products();
        product.setProductID(txtProductID.getText());
        product.setName(txtname.getText());
        product.setCategory(comboBoxCategory.getValue());
        product.setSize(comboBoxSize.getValue());
        product.setQtyOnHand(Double.parseDouble(txtQtyOnHand.getText()));
        product.setSupplierID(txtSupplierID.getText());
        product.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
        product.setImage(imageBytes);

        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        boolean addProduct = service.addProduct(product);
        if(addProduct){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "DONE");
            alert.show();
            paneForVboxes.setVisible(false);
            paneForVboxes.setManaged(false);
            vboxForaddProduct.setVisible(false);
            vboxForaddProduct.setManaged(false);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "NOT DONE !");
            alert.show();
        }
    }

    @FXML
    void btnOnActionAddProduct(ActionEvent event) {
        paneForVboxes.setVisible(true);
        paneForVboxes.setManaged(true);
        vboxForaddProduct.setVisible(true);
        vboxForaddProduct.setManaged(true);
    }

    @FXML
    void btnOnActionDeleteProduct(ActionEvent event) {

    }

    @FXML
    void btnOnActionUpdateProduct(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigationEmplyee(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) addButton.getScene().getWindow();
        primaryStage.hide();

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminEmployee.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnOnActionNavigationToSuppliers(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) addButton.getScene().getWindow();
        primaryStage.hide();

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminSupplier.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void BtnOnActionNavigationToReports(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) addButton.getScene().getWindow();
        primaryStage.hide();

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminReports.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnOnActionProductWindow(ActionEvent actionEvent) {

    }
}
