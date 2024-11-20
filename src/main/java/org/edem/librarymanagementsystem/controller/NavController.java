package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.edem.librarymanagementsystem.App;

public class NavController {

    @FXML
    private VBox content;

    @FXML
    private Label route_addBooks;

    @FXML
    private Label route_addPatron;

    @FXML
    private Label route_addReservation;

    @FXML
    private Label route_addTransaction;

    @FXML
    private Label route_books;

    @FXML
    private Label route_categories;

    @FXML
    private Label route_patrons;

    @FXML
    private Label route_transactions;

    @FXML
    private Label routre_reservations;

    @FXML
    void navAddBooks(MouseEvent event) {
        loadPage("transactionForm.fxml");
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
        loadPage("transactionForm.fxml");
    }

    @FXML
    void navBooks(MouseEvent event) {
        loadPage("books.fxml");
    }

    @FXML
    void navCategories(MouseEvent event) {

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
