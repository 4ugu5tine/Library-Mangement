package org.edem.librarymanagementsystem.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.service.BookService;

public class BookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book, String> column_author;

    @FXML
    private TableColumn<Book, Boolean> column_available;

    @FXML
    private TableColumn<Book, String> column_genre;

    @FXML
    private TableColumn<Book, Integer> column_id;

    @FXML
    private TableColumn<Book, String> column_title;

    @FXML
    private TableColumn<Book, Integer> column_year;

    @FXML
    private Button delete_book;

    @FXML
    private Button update_book;

    @FXML
    void delete_book(MouseEvent event) {

    }

    @FXML
    void update_book(MouseEvent event) {

    }

    private ObservableList<Book> bookList = FXCollections.observableArrayList();



    @FXML
    void initialize() {
        column_author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        column_available.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("isAvailable"));
        column_genre.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
        column_id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        column_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        column_year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearPublished"));


        LinkedList<Book> books  = BookService.getAllBooks();

        bookList.addAll(books);

        book_table.setItems(bookList);
    }

}
