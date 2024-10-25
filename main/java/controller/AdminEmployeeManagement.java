package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.SuperService;
import service.custom.UserService;
import service.custom.impl.UserServiceImpl;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminEmployeeManagement implements Initializable {


    @FXML
    public JFXButton btnAddNew;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private GridPane insideGridPane;

    @FXML
    private Pane paneOutside;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmailOfEmp;

    @FXML
    private JFXTextField txtEmailOfEmpUpdate;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNameOfEmp;

    @FXML
    private JFXTextField txtNameOfEmpUpdate;

    @FXML
    private JFXTextField txtNumber;

    @FXML
    private JFXTextField txtPasswordOfEmp;

    @FXML
    private JFXTextField txtPasswordOfEmpUpdate;

    @FXML
    private JFXTextField txtSearchEmployee;

    @FXML
    private VBox updatreVbox;

    @FXML
    private VBox vboxInside;

    private Integer userIDUpdate;

    @FXML
    void btnOnActionAddNewEmployee(ActionEvent event) {
        paneOutside.setVisible(true);
        paneOutside.setManaged(true);
        vboxInside.setVisible(true);
        vboxInside.setManaged(true);
    }


    @FXML
    void btnOnActionCancel(ActionEvent event) {
        paneOutside.setVisible(false);
        paneOutside.setManaged(false);
        vboxInside.setVisible(false);
        vboxInside.setManaged(false);
    }

    @FXML
    void btnOnActionCancelEmplyeeeUpdate(ActionEvent event) {
        paneOutside.setVisible(false);
        paneOutside.setManaged(false);
        updatreVbox.setVisible(false);
        updatreVbox.setManaged(false);
    }

    @FXML
    void btnOnActionSearchEmplyee(ActionEvent event) {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        Users users = service.findByNameOrId(txtSearchEmployee.getText());
        userIDUpdate = users.getUserID();
        txtNameOfEmpUpdate.setText(users.getName());
        txtEmailOfEmpUpdate.setText(users.getEmail());
        txtPasswordOfEmpUpdate.setText(users.getPassword());
    }


    @FXML
    void btnOnActionAddNewEmployeeUser(ActionEvent actionEvent) {
        UserServiceImpl userService = ServiceFactory.getInstance().getService(ServiceType.USER);

        Users users = new Users(null,
                txtNameOfEmp.getText(),
                txtEmailOfEmp.getText(),
                txtPasswordOfEmp.getText()
        );
        boolean isAdded = userService.addUser(users);
        if (isAdded){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
            showUsers();
            paneOutside.setVisible(false);
            paneOutside.setManaged(false);
            vboxInside.setVisible(false);
            vboxInside.setManaged(false);
        }else{
            new Alert(Alert.AlertType.ERROR,"Not Done").show();
        }
    }

    @FXML
    void btnOnActionUpdateEmployeeUser(ActionEvent actionEvent) {
        UserServiceImpl userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        Users users = new Users(userIDUpdate,
                txtNameOfEmpUpdate.getText(),
                txtEmailOfEmpUpdate.getText(),
                txtPasswordOfEmpUpdate.getText()
        );
        boolean isAdded = userService.updateUser(users);
        if (isAdded){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
            showUsers();
            paneOutside.setVisible(false);
            paneOutside.setManaged(false);
            vboxInside.setVisible(false);
            vboxInside.setManaged(false);
        }else{
            new Alert(Alert.AlertType.ERROR,"Not Done").show();
        }

    }

    @FXML
    void btnOnActionUpdateWindow(ActionEvent actionEvent) {
        paneOutside.setVisible(true);
        paneOutside.setManaged(true);
        updatreVbox.setVisible(true);
        updatreVbox.setManaged(true);

    }

    @FXML
    void btnOnActionDeleteEmployeeUser(ActionEvent actionEvent) {
        UserServiceImpl userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        Boolean b = userService.deleteUserById(userIDUpdate);
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
            showUsers();
        }else{
            new Alert(Alert.AlertType.INFORMATION,"NotDone").show();
        }
    }


    public void showUsers() {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        List<Users> users = service.getAll();
        insideGridPane.getChildren().clear();

        int rowIndex = 0;

        for (Users user : users) {
            HBox userRow = new HBox(10);
            userRow.setPadding(new Insets(15));
            Label userIdLabel = new Label(String.valueOf(user.getUserID()));
            Label userNameLabel = new Label(user.getName());
            Label userEmailLabel = new Label(user.getEmail());

            userIdLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e8b57; -fx-font-size: 14px;");
            userNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
            userEmailLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");

            userRow.setStyle("-fx-background-color: #f9f9f9; "
                    + "-fx-border-color: #ccc; "
                    + "-fx-border-radius: 5; "
                    + "-fx-padding: 15; "
                    + "-fx-spacing: 25;");

            userRow.getChildren().addAll(userIdLabel, userNameLabel, userEmailLabel);

            insideGridPane.add(userRow, 0, rowIndex++);
        }

        scrollPane.setContent(insideGridPane);
        scrollPane.setFitToWidth(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
    }

    @FXML
    void btnOnActionReports(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) btnAddNew.getScene().getWindow();
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
    void btnOnActionSupplierView(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) btnAddNew.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminSupplier.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionToProduct(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) btnAddNew.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
