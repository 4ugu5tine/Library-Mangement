package org.edem.librarymanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.edem.librarymanagementsystem.entities.Reservation;
import org.edem.librarymanagementsystem.service.ReservationService;

public class ReservationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Reservation> reservation_table;
    @FXML
    private TableColumn<Reservation, Integer> column_bookId;

    @FXML
    private TableColumn<Reservation, LocalDate> column_date;

    @FXML
    private TableColumn<Reservation, Integer> column_reservationId;

    @FXML
    private TableColumn<Reservation, Integer> column_userId;

    @FXML
    private Button delete_reservation;

    @FXML
    private Button update_reservation;

    @FXML
    void delete_reservation(MouseEvent event) {

    }

    @FXML
    void update_reservation(MouseEvent event) {

    }

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    public void initialize() {
        column_date.setCellValueFactory(new PropertyValueFactory<Reservation, LocalDate>("date"));
        column_reservationId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("reservationId"));
        column_userId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("userId"));
        column_bookId.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("bookId"));

        LinkedList<Reservation> reservations = ReservationService.getAllReservations();

        reservationList.addAll(reservations);

        reservation_table.setItems(reservationList);
    }
}
