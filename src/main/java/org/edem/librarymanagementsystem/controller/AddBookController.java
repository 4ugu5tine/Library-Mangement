package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.service.BookService;

import java.io.IOException;
import java.sql.SQLException;

public class AddBookController {

    @FXML
    private TextField author;

    @FXML
    private TextField copies;

    @FXML
    private Button create;

    @FXML
    private Label error_author;

    @FXML
    private Label error_copies;

    @FXML
    private Label error_genre;

    @FXML
    private Label error_publisher;

    @FXML
    private Label error_title;

    @FXML
    private Label error_year;

    @FXML
    private TextField genre;

    @FXML
    private TextField publisher;

    @FXML
    private TextField title;

    @FXML
    private TextField year;

    @FXML
    void create(MouseEvent event) throws IOException, SQLException {
        if(title.getText().isEmpty()){
            error_title.setText("Title cannot be empty");
        }
        else if (author.getText().isEmpty()){
            error_author.setText("Author cannot be empty");
        }
        else if(genre.getText().isEmpty()){
            error_genre.setText("Genre cannot be empty");
        }
        else if (publisher.getText().isEmpty()) {
            error_publisher.setText("Publisher cannot be empty");
        }
        else if(year.getText().isEmpty()){
            error_year.setText("Year published cannot be empty");
        }
        else if(copies.getText().isEmpty()){
            error_copies.setText("Copies cannot be empty");
        }

        else {
            error_title.setText("");
            error_author.setText("");
            error_genre.setText("");
            error_publisher.setText("");
            error_year.setText("");
            error_copies.setText("");

            BookService.addBook(title.getText(), author.getText(),publisher.getText(),Integer.parseInt(year.getText()),genre.getText(),Integer.parseInt(copies.getText()));
            App.setRoot("layout");
        }
    }

}
