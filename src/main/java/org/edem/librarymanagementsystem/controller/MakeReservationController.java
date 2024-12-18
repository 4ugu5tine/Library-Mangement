package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.service.ReservationService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class MakeReservationController {
    private final ReservationService reservationService = new ReservationService();

    @FXML
    private TextField bookid;

    @FXML
    private Button create;

    @FXML
    private DatePicker date;

    @FXML
    private Label error_bookid;

    @FXML
    private Label error_date;

    @FXML
    private Label error_patronid;

    @FXML
    private TextField patronid;

    @FXML
    void create(MouseEvent event) throws IOException {
        if(bookid.getText().isEmpty()){
            error_bookid.setText("Book ID cannot be blank");
        }
        else if(patronid.getText().isEmpty()){
            error_patronid.setText("Patron ID cannot be blank");
        }
        else if (date.getValue() == null){
            error_date.setText("Date cannot be blank");
        } else {
            error_bookid.setText("");
            error_patronid.setText("");
            error_date.setText("");


            reservationService.createReservation(Integer.parseInt(patronid.getText()), Integer.parseInt(bookid.getText()), Date.valueOf(date.getValue()));
            App.setRoot("layout");
        }
    }

}
