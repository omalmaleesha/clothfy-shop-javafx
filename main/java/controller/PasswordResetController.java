package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.EmailSender;
import util.GenerateOtp;

import java.io.IOException;

public class PasswordResetController {

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField otp;

    @FXML
    private JFXButton sendOtpButton;

    @FXML
    private JFXButton varifyOtp;

    private String generatedOTP;

    @FXML
    void btnONActionVarify(ActionEvent event) {
        if(otp.getText().equals(generatedOTP)){
            Stage stage = new Stage();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ResetPassword.fxml"));
                Parent root = loader.load();

                ResetPasswordUpdateController resetPasswordController = loader.getController();
                resetPasswordController.setEmail(emailField.getText());

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void btnOnActionSendOTP(ActionEvent event) {
        generatedOTP = GenerateOtp.generateOTP(4);
        EmailSender.sendEmail(emailField.getText(),generatedOTP);
    }

    public void setEmail(String text) {
        emailField.setText(text);
    }
}
