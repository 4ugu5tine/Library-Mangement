package org.edem.librarymanagementsystem.entities;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;

public class Patron {
    private int patronId;
    private String name;
    private String email;
    private String phone;

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Patron(int patronId, String name, String email, String phone) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // CRUD Operations

    // Other CRUD methods...
}

