package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class BookService {

    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, author, publisher, yearPublished, isAvailable, genreId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYearPublished());
            statement.setBoolean(5, book.isAvailable());
            statement.setInt(6, book.getGenreId());
            statement.executeUpdate();
        }
    }

    public static Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("bookId"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("yearPublished"),
                        rs.getBoolean("isAvailable"),
                        rs.getInt("genreId")
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }


    public static LinkedList<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        LinkedList<Book> results = new LinkedList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int bookId = rs.getInt("bookId");
                String title = rs.getString("title");
                int genreId = rs.getInt("genreId");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int yearPublished = rs.getInt("yearPublished");
                boolean isAvailable = rs.getBoolean("isAvailable");

                results.add(new Book(bookId ,title,author,publisher,yearPublished, isAvailable,genreId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
