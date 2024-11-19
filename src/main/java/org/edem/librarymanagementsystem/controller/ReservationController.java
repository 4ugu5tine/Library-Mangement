package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ReservationController {

    @FXML
    private TextField bookId_field;

    @FXML
    private DatePicker date_field;

    @FXML
    private Button reserveButton;

    @FXML
    private TextField userId_field;

    @FXML
    public void handleReserveButtonAction() {
        String userId = userId_field.getText();
        String bookId = bookId_field.getText();
        LocalDate reservationDate = date_field.getValue();

        // Validate User ID
        if (userId == null || userId.trim().isEmpty() || !isNumeric(userId)) {
            showAlert("Validation Error", "Please enter a valid User ID.");
            return;
        }

        // Validate Book ID
        if (bookId == null || bookId.trim().isEmpty() || !isNumeric(bookId)) {
            showAlert("Validation Error", "Please enter a valid Book ID.");
            return;
        }

        // Validate Date
        if (reservationDate == null) {
            showAlert("Validation Error", "Please select a reservation date.");
            return;
        }

        // If all validations pass
        System.out.println("Reservation successful for User ID: " + userId + ", Book ID: " + bookId + ", Date: " + reservationDate);
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
