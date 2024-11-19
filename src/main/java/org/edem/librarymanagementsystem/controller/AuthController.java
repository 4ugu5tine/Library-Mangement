package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.service.UserService;

public class AuthController {

    @FXML
    private TextField email_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button loginButton;

    public void handleLoginButtonAction() {
        String email = email_field.getText();
        String password = password_field.getText();

        // Basic validation
        if (email == null || email.isEmpty()) {
            showAlert("Validation Error", "Username cannot be empty.");
            return;
        }

        if (password == null || password.isEmpty()) {
            showAlert("Validation Error", "Password cannot be empty.");
            return;
        }

        User user = UserService.login(email,password);


        // Proceed with login logic
        System.out.println("Login successful! Username: " + user.getName());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}