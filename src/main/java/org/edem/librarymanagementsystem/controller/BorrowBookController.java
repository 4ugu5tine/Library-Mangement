package org.edem.librarymanagementsystem.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.edem.librarymanagementsystem.service.TransactionService;

import java.time.LocalDate;

public class BorrowBookController {
    @FXML
    private TextField userId_field;

    @FXML
    private TextField bookId_field;


    @FXML
    private Button borrowButton;

    @FXML
    private void handleBorrowBook() {
        String userId = userId_field.getText();
        String bookId = bookId_field.getText();


        // Validate input
        if (userId == null || userId.isEmpty() || bookId == null || bookId.isEmpty()) {
            showAlert("Validation Error", "All fields are required.");
            return;
        }

        // Call borrowBook logic
        int userIdInt = Integer.parseInt(userId);
        int bookIdInt = Integer.parseInt(bookId);
        TransactionService.borrowBook(bookIdInt, userIdInt);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
