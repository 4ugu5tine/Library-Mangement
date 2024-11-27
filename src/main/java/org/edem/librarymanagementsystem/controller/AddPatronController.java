package org.edem.librarymanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.edem.librarymanagementsystem.App;
import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.service.UserService;

import java.io.IOException;

public class AddPatronController {

    @FXML
    private TextField address;

    @FXML
    private Button create;

    @FXML
    private TextField email;

    @FXML
    private Label error_address;

    @FXML
    private Label error_email;

    @FXML
    private Label error_name;

    @FXML
    private Label error_phone;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    void create(MouseEvent event) throws IOException {
        if(name.getText().isEmpty()){
            error_name.setText("Name is required");
        }
        else if(email.getText().isEmpty()){
            error_email.setText("Email is required");
        }
        else if(phone.getText().isEmpty()){
            error_phone.setText("Phone required");
        }
        else if(address.getText().isEmpty()){
            error_address.setText("Address is required");
        }
        else{
            error_name.setText("");
            error_email.setText("");
            error_address.setText("");
            error_phone.setText("");

            UserService.createPatron(name.getText(), email.getText(), phone.getText(),address.getText());
            App.setRoot("layout");
        }
    }

}
