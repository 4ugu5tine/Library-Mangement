package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Librarian;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianService {
    // CRUD Operations
    public static void addLibrarian(Librarian librarian) throws SQLException {
        String sql = "INSERT INTO librarian (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, librarian.getUsername());
            stmt.setString(2, librarian.getEmail());
            stmt.setString(3, librarian.getPassword());
            stmt.executeUpdate();
        }
    }

    public static Librarian getLibrarianById(int librarianId) throws SQLException {
        String sql = "SELECT * FROM librarian WHERE librarianId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, librarianId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Librarian(
                        rs.getInt("librarianId"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        }
        return null;
    }
}