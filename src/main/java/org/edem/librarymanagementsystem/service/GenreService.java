package org.edem.librarymanagementsystem.service;


import org.edem.librarymanagementsystem.entities.Genre;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenreService {
    public static void addGenre(Genre genre) throws SQLException {
        String sql = "INSERT INTO books (name, description) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, genre.getName());
            statement.setString(2, genre.getDescription());
            statement.executeUpdate();
        }
    }
}
