package org.edem.librarymanagementsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.edem.librarymanagementsystem.entities.Librarian;

import java.util.LinkedList;

public class LibrarianController {



    @FXML
    private TableColumn<Librarian, String> email_column;

    @FXML
    private TableColumn<Librarian, Integer> id_column;

    @FXML
    private TableView<Librarian> librarian_table;

    @FXML
    private TableColumn<Librarian, String> username_column;

    private ObservableList<Librarian> librarianList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        email_column.setCellValueFactory(new PropertyValueFactory<Librarian, String>("email"));
        id_column.setCellValueFactory(new PropertyValueFactory<Librarian, Integer>("librarianId"));
        username_column.setCellValueFactory(new PropertyValueFactory<Librarian, String>("username"));

        LinkedList<Librarian> librarians  = LibrarianService.getAllLibrarians();

        librarianList.addAll(librarians);

        librarian_table.setItems(librarianList);

    }

}

