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
        String sql = "INSERT INTO books (title, author, publisher, yearPublished, isAvailable, genre,copies) VALUES (?, ?, ?, ?, TRUE,?, 0)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getYearPublished());
            statement.setBoolean(5, book.isAvailable());
            statement.setString(6, book.getGenre());
            statement.setInt(7, book.getCopies());
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
                        rs.getString("genreId"),
                        rs.getInt("copies")
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;

    }
    public static LinkedList<Book> searchBooks(String query) {
        String sql = """
        SELECT * FROM books 
        WHERE LOWER(title) LIKE LOWER(?) 
        OR LOWER(author) LIKE LOWER(?) 
    """;
        LinkedList<Book> results = new LinkedList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";


            statement.setString(1, searchPattern);  // title
            statement.setString(2, searchPattern);  // author

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("bookId");  // assuming snake_case in database
                String title = rs.getString("title");
                String genreId = rs.getString("genre");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int yearPublished = rs.getInt("year_published");
                boolean isAvailable = rs.getBoolean("isAvailable");
                int copies = rs.getInt("copies");

                results.add(new Book(
                        bookId,
                        title,
                        author,
                        publisher,
                        yearPublished,
                        isAvailable,
                        genreId,
                        copies
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
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
                String genre = rs.getString("genre");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int yearPublished = rs.getInt("yearPublished");
                boolean isAvailable = rs.getBoolean("isAvailable");
                int copies = rs.getInt("copies");

                results.add( new Book( bookId,  title,  author,  publisher,  yearPublished,  isAvailable,  genre,  copies));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
