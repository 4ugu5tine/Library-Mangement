package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.service.UserService;

import java.io.IOException;

public class AuthController {
    private final UserService userService = new UserService();

    @FXML
    private TextField email_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button loginButton;

    @FXML
    public void login() throws IOException {
        String email = email_field.getText();
        String password = password_field.getText();


        if (email == null || email.isEmpty()) {
            showAlert("Validation Error", "Username cannot be empty.");
            return;
        }

        if (password == null || password.isEmpty()) {
            showAlert("Validation Error", "Password cannot be empty.");
            return;
        }

        boolean user = userService.login(email,password);

        if(!user){
            showAlert("Validation Error", "Email or password incorrect");
        } else {
            App.setRoot("layout");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}