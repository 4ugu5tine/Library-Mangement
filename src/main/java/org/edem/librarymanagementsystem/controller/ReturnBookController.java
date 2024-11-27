package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.service.TransactionService;

import java.io.IOException;

public class ReturnBookController {

    @FXML
    private TextField bookid;

    @FXML
    private Button borrow;

    @FXML
    private Label error_bookid;

    @FXML
    private Label error_transactionid;

    @FXML
    private TextField transactionid;

    @FXML
    void create(MouseEvent event) throws IOException {
        if(bookid.getText().isEmpty()){
            error_bookid.setText("Book ID cannot be null");
        }
        else if (transactionid.getText().isEmpty()){
            error_transactionid.setText("Transaction ID cannot be null");
        }
        else {
            error_transactionid.setText("");
            error_bookid.setText("");

            TransactionService.returnBook(Integer.parseInt(transactionid.getText()),Integer.parseInt(bookid.getText()));
            App.setRoot("layout");
        }
    }

}
