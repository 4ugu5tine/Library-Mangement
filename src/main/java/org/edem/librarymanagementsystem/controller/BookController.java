package org.edem.librarymanagementsystem.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.entities.Librarian;
import org.edem.librarymanagementsystem.service.BookService;
import org.edem.librarymanagementsystem.service.LibrarianService;

public class BookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Book, String> column_author;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book, Boolean> column_available;

    @FXML
    private TableColumn<Book, Integer> column_genre;

    @FXML
    private TableColumn<Book, Integer> column_id;

    @FXML
    private TableColumn<Book, String> column_publisher;

    @FXML
    private TableColumn<Book, String> column_title;

    @FXML
    private TableColumn<Book, Integer> column_year;


    private ObservableList<Book> bookList = FXCollections.observableArrayList();



    @FXML
    void initialize() {
        column_author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        column_available.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("isAvailable"));
        column_genre.setCellValueFactory(new PropertyValueFactory<Book, Integer>("genreId"));
        column_id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookId"));
        column_publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        column_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        column_year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearPublished"));


        LinkedList<Book> books  = BookService.getAllBooks();

        bookList.addAll(books);

        book_table.setItems(bookList);
    }

}
