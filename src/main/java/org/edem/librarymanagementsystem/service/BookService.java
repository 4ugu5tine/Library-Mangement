package org.edem.librarymanagementsystem.service;

import lombok.Generated;
import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static java.lang.Boolean.TRUE;

public class BookService {
    private DatabaseConnection databaseConnection = new DatabaseConnection() ;

    public BookService(DatabaseConnection mockDatabaseConnection) {
        this.databaseConnection = mockDatabaseConnection;
    }

    @Generated
    public BookService() {
    }

    public void addBook(String title, String author, String publisher, int yearPublished, String genre, int copies) throws SQLException {
        String sql = "INSERT INTO books (title, author, publisher, yearPublished,isAvailable, genre,copies ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setString(2,author);
            statement.setString(3,publisher);
            statement.setInt(4, yearPublished);
            statement.setBoolean(5,TRUE);
            statement.setString(6, genre);
            statement.setInt(7, copies);
            statement.executeUpdate();
        }
    }

    public  Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM books WHERE bookId = ?";
        try (Connection conn = databaseConnection.getConnection();
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

    public  LinkedList<Book> searchBooks(String query) {
        String sql = """
        SELECT * FROM books 
        WHERE LOWER(title) LIKE LOWER(?) 
        OR LOWER(author) LIKE LOWER(?) 
    """;
        LinkedList<Book> results = new LinkedList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            String searchPattern = "%" + query + "%";


            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("bookId");
                String title = rs.getString("title");
                String genreId = rs.getString("genre");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int yearPublished = rs.getInt("year_published");
                boolean isAvailable = rs.getBoolean("isavailable");
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


    public LinkedList<Book> getAllBooks() {
        String sql = "SELECT * FROM books";
        LinkedList<Book> results = new LinkedList<>();

        try (Connection conn = databaseConnection.getConnection();
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

    public  void deleteBook(int bookId){
        String sql = "DELETE FROM books WHERE bookId = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateBook(int bookId, String title, String author, int yearPublished, String genre, int copies) throws SQLException {
        String sql = """
        UPDATE books
        SET title = ?, author = ?, yearPublished = ?, genre = ?, copies = ?
        WHERE bookId = ?
    """;

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, yearPublished);
            statement.setString(4, genre);
            statement.setInt(5, copies);
            statement.setInt(6, bookId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated < 1) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        }
    }

}
