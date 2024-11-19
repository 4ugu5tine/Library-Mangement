package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.service.TransactionService;

public class ReturnBookController {

    @FXML
    private TextField transactionId_field;

    @FXML
    private TextField bookId_field;

    @FXML
    private Button returnButton;

    @FXML
    private Label validationMessage;

    @FXML
    public void handleReturnBook() {
        String transactionIdText = transactionId_field.getText();
        String bookIdText = bookId_field.getText();

        if (transactionIdText.isEmpty() || bookIdText.isEmpty()) {
            validationMessage.setText("Both fields must be filled out.");
            validationMessage.setVisible(true);
            return;
        }

        try {
            int transactionId = Integer.parseInt(transactionIdText);
            int bookId = Integer.parseInt(bookIdText);

            Transaction transaction = TransactionService.returnBook(transactionId, bookId);

            if (transaction != null) {
                validationMessage.setText("Book returned successfully!");
                validationMessage.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                validationMessage.setText("Failed to return book. Please check the transaction ID and book ID.");
                validationMessage.setVisible(true);
            }
        } catch (NumberFormatException e) {
            validationMessage.setText("Invalid transaction ID or book ID.");
            validationMessage.setVisible(true);
        }
    }
}
