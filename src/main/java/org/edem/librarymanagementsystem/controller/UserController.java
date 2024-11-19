package org.edem.librarymanagementsystem.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.service.UserService;

import java.util.LinkedList;

public class UserController {

    @FXML
    private TableView<User> user_table;

    @FXML
    private TableColumn<User, Integer> column_userId;

    @FXML
    private TableColumn<User, String> column_name;

    @FXML
    private TableColumn<User, String> column_email;

    @FXML
    private TableColumn<User, String> column_phone;

    @FXML
    private TableColumn<User, String> column_address;

    @FXML
    private TableColumn<User, String> column_accountType;

    @FXML
    private TableColumn<User, Integer> column_borrowedBooks;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        column_userId.setCellValueFactory(new PropertyValueFactory<User, Integer>("userId"));
        column_name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        column_email.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        column_phone.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));
        column_address.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        column_accountType.setCellValueFactory(new PropertyValueFactory<User, String>("accountType"));
        column_borrowedBooks.setCellValueFactory(new PropertyValueFactory<User, Integer>("borrowedBooks"));

        LinkedList<User> users = UserService.findAllPatrons();

        userList.addAll(users);
        user_table.setItems(userList);
    }

}
