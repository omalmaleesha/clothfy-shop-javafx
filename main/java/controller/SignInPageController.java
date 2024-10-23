package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.UserService;
import service.custom.impl.UserServiceImpl;
import util.ServiceType;

import java.io.IOException;

public class SignInPageController {

    @FXML
    public JFXButton loginbtn;

    @FXML
    public JFXButton forget;

    @FXML
    private VBox createAccountComponent;

    @FXML
    private Text lblNow;

    @FXML
    private Text lblNowStatus;

    @FXML
    private VBox logInComponent;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField txtCreateEmail;

    @FXML
    private JFXTextField txtCreateName;

    @FXML
    private JFXTextField txtCreatePassword;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtEmail;



    @FXML
    void btnOnActionCreateAccount(ActionEvent event) {
        UserServiceImpl userService = ServiceFactory.getInstance().getService(ServiceType.USER);

        Users users = new Users(null,
                txtCreateName.getText(),
                txtCreateEmail.getText(),
                txtCreatePassword.getText()
                );
        boolean isAdded = userService.addUser(users);
        if(isAdded){
            txtCreateName.clear();
            txtCreateEmail.clear();
            txtCreatePassword.clear();
            changeToSignUp();
        }

    }


    private void changeToSignUp() {
        lblNowStatus.setText("Don't have Account ?");
        lblNow.setText("SignUp");

        stackPane.getChildren().clear();
        stackPane.getChildren().add(logInComponent);
    }


    @FXML
    void btnOnActionLogIn(ActionEvent event) {
        boolean logged = logIn(txtEmail.getText(), txtPassword.getText());
        if (logged) {
            Stage primaryStage = (Stage) loginbtn.getScene().getWindow();
            primaryStage.hide();

            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/empViewProduct.fxml"))));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    public void handleLabelClick(MouseEvent mouseEvent) {
        handleLabelClick();
    }

    public void handleLabelClick() {
        if (lblNow.getText().equals("SignIn")) {
            lblNowStatus.setText("Don't have Account ?");
            lblNow.setText("SignUp");

            stackPane.getChildren().clear();
            stackPane.getChildren().add(logInComponent);

        } else if (lblNow.getText().equals("SignUp")) {
            lblNowStatus.setText("Already have An Account ?");
            lblNow.setText("SignIn");

            stackPane.getChildren().clear();
            stackPane.getChildren().add(createAccountComponent);
        }
    }


    private boolean logIn(String email, String password) {
        UserServiceImpl userService = ServiceFactory.getInstance().getService(ServiceType.USER);
        boolean conformed = userService.conformUser(email, password);
        return conformed;
    }


    @FXML
    void btnOnActionForgetPw(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PasswordReset.fxml"));
            Parent root = loader.load();

            PasswordResetController passwordResetController = loader.getController();
            passwordResetController.setEmail(txtEmail.getText());

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
