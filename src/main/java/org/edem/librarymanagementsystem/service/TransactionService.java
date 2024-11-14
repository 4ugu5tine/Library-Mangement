package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;

public class TransactionService {

    public static void borrowBook(int bookId, int patronId, int librarianId) {
        String borrowSql = "INSERT INTO transaction (bookId, patronId, borrowDate, isReturned, librarianId) VALUES (?, ?, ?, ?, ?)";
        String updateBookSql = "UPDATE books SET isAvailable = FALSE WHERE bookId = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Borrow the book (insert transaction)
            try (PreparedStatement stmt = connection.prepareStatement(borrowSql)) {
                stmt.setInt(1, bookId);
                stmt.setInt(2, patronId);
                stmt.setDate(3, new Date(System.currentTimeMillis()));
                stmt.setBoolean(4, false); // Initially not returned
                stmt.setInt(5, librarianId);
                stmt.executeUpdate();
            }

            // Update book availability
            try (PreparedStatement stmt = connection.prepareStatement(updateBookSql)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void getAllTransactions() {
        String sql = "SELECT * FROM transaction";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int transactionId = rs.getInt("transactionId");
                int bookId = rs.getInt("bookId");
                int patronId = rs.getInt("patronId");
                Date borrowDate = rs.getDate("borrowDate");
                Date returnDate = rs.getDate("returnDate");
                boolean isReturned = rs.getBoolean("isReturned");
                int librarianId = rs.getInt("librarianId");

                System.out.println("Transaction ID: " + transactionId + ", Book ID: " + bookId + ", Patron ID: " + patronId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
