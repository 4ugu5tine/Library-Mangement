package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.edem.librarymanagementsystem.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class NavController {

    @FXML
    private VBox content;

    @FXML
    private Label route_addBook;

    @FXML
    private Label route_addBorrowBook;

    @FXML
    private Label route_addPatron;

    @FXML
    private Label route_addReservation;

    @FXML
    private Label route_books;

    @FXML
    private Label route_patrons;

    @FXML
    private Label route_reservations;

    @FXML
    private Label route_returnBook;

    @FXML
    private Label route_transactions;

    @FXML
    void navAddBooks(MouseEvent event) {
        loadPage("createBookForm.fxml");
    }

    @FXML
    void navAddPatron(MouseEvent event) {
        loadPage("createPatronForm.fxml");
    }

    @FXML
    void navAddReservation(MouseEvent event) {
        loadPage("reservationForm.fxml");
    }

    @FXML
    void navAddTransaction(MouseEvent event) {
        loadPage("borrowBookForm.fxml");
    }

    @FXML
    void navBooks(MouseEvent event) {
        loadPage("books.fxml");
    }

    @FXML
    void navPatrons(MouseEvent event) {
        loadPage("patron.fxml");
    }

    @FXML
    void navReservations(MouseEvent event) {
        loadPage("reservations.fxml");

    }

    @FXML
    void navReturnBook(MouseEvent event) {
        loadPage("returnBookForm.fxml");
    }

    @FXML
    void navTransactions(MouseEvent event) {
        loadPage("transactions.fxml");
    }

    public void loadPage (String pageName){
        try{
            FXMLLoader loader = new FXMLLoader(App.class.getResource(pageName));
            Parent page = loader.load();
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

