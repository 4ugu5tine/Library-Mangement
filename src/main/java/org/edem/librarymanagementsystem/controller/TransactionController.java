package org.edem.librarymanagementsystem.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.entities.Transaction;

import java.time.LocalDate;

public class TransactionController {

    @FXML
    private TableView<Transaction> transaction_table;

    @FXML
    private TableColumn<Transaction, Integer> column_transactionId;

    @FXML
    private TableColumn<Transaction, Integer> column_bookId;

    @FXML
    private TableColumn<Transaction, Integer> column_userId;

    @FXML
    private TableColumn<Transaction, LocalDate> column_borrowDate;

    @FXML
    private TableColumn<Transaction, LocalDate> column_returnDate;

    @FXML
    private TableColumn<Transaction, Boolean> column_isReturned;

    @FXML
    public void initialize() {
        // Set up the columns
        column_transactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        column_bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        column_userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        column_borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        column_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        column_isReturned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));

        // Dummy data for testing
        ObservableList<Transaction> transactions = FXCollections.observableArrayList(
                new Transaction(1, 101, 201, LocalDate.now().minusDays(5), LocalDate.now(), true),
                new Transaction(2, 102, 202, LocalDate.now().minusDays(10), null, false)
        );

        // Validate data before populating the table
        if (validateTransactions(transactions)) {
            transaction_table.setItems(transactions);
        } else {
            showAlert("Data Validation Error", "Some transactions contain invalid data.");
        }
    }

    /**
     * Validates a list of transactions.
     *
     * @param transactions The list of transactions to validate.
     * @return true if all transactions are valid; false otherwise.
     */
    private boolean validateTransactions(ObservableList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (!validateTransaction(transaction)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a single transaction.
     *
     * @param transaction The transaction to validate.
     * @return true if the transaction is valid; false otherwise.
     */
    private boolean validateTransaction(Transaction transaction) {
        if (transaction.getTransactionId() <= 0) {
            return false; // Transaction ID must be positive.
        }
        if (transaction.getBookId() <= 0) {
            return false; // Book ID must be positive.
        }
        if (transaction.getUserId() <= 0) {
            return false; // User ID must be positive.
        }
        if (transaction.getBorrowDate() == null) {
            return false; // Borrow date must not be null.
        }
        if (transaction.getReturnDate() != null && transaction.getReturnDate().isBefore(transaction.getBorrowDate())) {
            return false; // Return date must not be before borrow date.
        }
        return true;
    }

    /**
     * Displays an alert with the given title and message.
     *
     * @param title   The title of the alert.
     * @param message The message of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
