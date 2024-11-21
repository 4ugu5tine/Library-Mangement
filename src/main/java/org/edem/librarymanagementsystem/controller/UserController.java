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
import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.service.UserService;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<User> user_table;

    @FXML
    private TableColumn<User, String> column_address;

    @FXML
    private TableColumn<User, Integer> column_borrowed;

    @FXML
    private TableColumn<User, String> column_email;

    @FXML
    private TableColumn<User, Integer> column_id;

    @FXML
    private TableColumn<User, String> column_accountType;

    @FXML
    private TableColumn<User, String> column_name;

    @FXML
    private TableColumn<User, String> column_phone;

    @FXML
    private Button delete_user;

    @FXML
    private Button update_user;

    @FXML
    void delete_user(MouseEvent event) {

    }

    @FXML
    void update_user(MouseEvent event) {

    }



    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        column_id.setCellValueFactory(new PropertyValueFactory<User, Integer>("userid"));
        column_name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        column_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        column_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        column_phone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        column_address.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        column_accountType.setCellValueFactory(new PropertyValueFactory<User, String>("accountType"));
        column_borrowed.setCellValueFactory(new PropertyValueFactory<User, Integer>("borrowedbooks"));

        LinkedList<User> users = UserService.findAllPatrons();

        userList.addAll(users);
        user_table.setItems(userList);
    }

}
