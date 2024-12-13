package org.edem.librarymanagementsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.service.BookService;

public class BookController {
    private final BookService bookService = new BookService();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Book> book_table;

    @FXML
    private TableColumn<Book, String> column_author;

//    @FXML
//    private TableColumn<Book, String> column_publisher;

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
    private TableColumn<Book, Integer> column_copies;

    @FXML
    private Button delete_book;

    @FXML
    private Button update_book;

    @FXML
    void delete_book(MouseEvent event) {
        bookService.deleteBook(book_table.getSelectionModel().getSelectedItem().getBookId());
    }

    @FXML
    void update_book(MouseEvent event) throws SQLException {
        Book selectedBook = book_table.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            selectedBook.setTitle(column_title.getText());
            selectedBook.setAuthor(column_author.getText());
            selectedBook.setAuthor(column_author.getText());
//            selectedBook.setPublisher(column_publisher.getText());
            selectedBook.setYearPublished(Integer.parseInt(column_year.getText()));
            selectedBook.setGenre(column_genre.getText());
            selectedBook.setCopies(Integer.parseInt(column_copies.getText()));


            bookService.updateBook(Integer.parseInt(column_id.getText()) , column_title.getText(),column_author.getText(),Integer.parseInt(column_year.getText()) , column_genre.getText(), Integer.parseInt(column_copies.getText()));
        }
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
//        column_publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));


        LinkedList<Book> books  = bookService.getAllBooks();

        bookList.addAll(books);

        book_table.setItems(bookList);
    }

}
