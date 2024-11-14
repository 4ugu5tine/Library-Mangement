package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Patron;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatronService {
    public static void addPatron(Patron patron) throws SQLException {
        String sql = "INSERT INTO patron (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getEmail());
            stmt.setString(3, patron.getPhone());
            stmt.executeUpdate();
        }
    }

    public static Patron getPatronById(int patronId) throws SQLException {
        String sql = "SELECT * FROM patron WHERE patronId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patronId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patron(
                        rs.getInt("patronId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }
        }
        return null;
    }
}
