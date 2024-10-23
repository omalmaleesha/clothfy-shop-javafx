package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import service.ServiceFactory;
import service.SuperService;
import service.custom.UserService;
import util.ServiceType;

public class ResetPasswordUpdateController {

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    void btnOnActionDone(ActionEvent event) {
        UserService service = ServiceFactory.getInstance().getService(ServiceType.USER);
        boolean b = service.updatePassword(txtPassword.getText(), txtEmail.getText());
        if(b){
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
        }
    }

    public void setEmail(String text) {
        txtEmail.setText(text);
    }
}
