package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.service.TransactionService;

import java.io.IOException;

public class BorrowBookController {

    @FXML
    private TextField bookid;

    @FXML
    private Button borrow;

    @FXML
    private Label error_bookid;

    @FXML
    private Label error_patronid;

    @FXML
    private TextField patronId;

    @FXML
    void borrow(MouseEvent event) throws IOException {
        if(patronId.getText().isEmpty()){
            error_patronid.setText("Patron Id cannot be empty");
        }
        else if(bookid.getText().isEmpty()){
            error_bookid.setText("Book Id cannot be empty");
        }
        else {
            error_bookid.setText("");
            error_patronid.setText("");

            TransactionService.borrowBook(Integer.parseInt(bookid.getText()),Integer.parseInt(patronId.getText()));
            App.setRoot("transactions");
        }
    }

}
