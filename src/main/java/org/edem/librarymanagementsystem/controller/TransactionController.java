package org.edem.librarymanagementsystem.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.service.TransactionService;

import java.time.LocalDate;
import java.util.LinkedList;

public class TransactionController {
    private final TransactionService transactionService = new TransactionService();

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

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        column_transactionId.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        column_bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        column_userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        column_borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        column_returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        column_isReturned.setCellValueFactory(new PropertyValueFactory<>("isReturned"));

        LinkedList<Transaction> transactions = transactionService.getAllTransactions();
        transactionList.addAll(transactions);
        transaction_table.setItems(transactionList);
    }


    private boolean validateTransactions(ObservableList<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            if (!validateTransaction(transaction)) {
                return false;
            }
        }
        return true;
    }


    private boolean validateTransaction(Transaction transaction) {
        if (transaction.getTransactionId() <= 0) {
            return false;
        }
        if (transaction.getBookId() <= 0) {
            return false;
        }
        if (transaction.getUserId() <= 0) {
            return false;
        }
        if (transaction.getBorrowDate() == null) {
            return false;
        }
        if (transaction.getReturnDate() != null && transaction.getReturnDate().isBefore(transaction.getBorrowDate())) {
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void delete_transaction(MouseEvent mouseEvent) {

    }

    public void update_transaction(MouseEvent mouseEvent) {
    }
}
