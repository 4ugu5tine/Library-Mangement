package org.edem.librarymanagementsystem.entities;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;

public class Librarian {
    private int librarianId;
    private String username;
    private String email;
    private String password;

    public Librarian(int librarianId, String username, String email) {
        this.librarianId = librarianId;
        this.username = username;
        this.email = email;
    }

    // Constructors, Getters, Setters

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Librarian(int librarianId, String username, String email, String password) {
        this.librarianId = librarianId;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    // Other CRUD methods...
}
