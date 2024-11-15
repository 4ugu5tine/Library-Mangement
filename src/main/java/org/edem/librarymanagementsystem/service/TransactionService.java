package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;

public class TransactionService {


    public static void getAllTransactions() {
        String sql = "SELECT * FROM transaction";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
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

    public static void borrowBook(int bookId, int patronId, int librarianId) {
        String borrowSql = "INSERT INTO transaction (bookId, patronId, borrowDate, isReturned, librarianId) VALUES (?, ?, ?, ?, ?)";
        String updateBookSql = "UPDATE books SET isAvailable = FALSE WHERE bookId = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(borrowSql)) {
                statement.setInt(1, bookId);
                statement.setInt(2, patronId);
                statement.setDate(3, new Date(System.currentTimeMillis()));
                statement.setBoolean(4, false); // Initially not returned
                statement.setInt(5, librarianId);
                statement.executeUpdate();
            }

            try (PreparedStatement statement = connection.prepareStatement(updateBookSql)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
            }

            connection.commit();
            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public boolean returnBook(int transactionId, int bookId) {
        String returnSql = "UPDATE transactions SET isReturned = TRUE, returnDate = CURRENT_DATE WHERE transactionId = ?";
        String updateBookQuery = "UPDATE books SET isAvailable = TRUE WHERE bookId = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try(
                    PreparedStatement transactionStatement = connection.prepareStatement(returnSql);
                    PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery))
            {

                transactionStatement.setInt(1,transactionId);

                int transactionUpdate = transactionStatement.executeUpdate();

                bookStatement.setInt(1,bookId);
                int bookUpdate = bookStatement.executeUpdate();

                // If both updates are successful, return true
                return transactionUpdate > 0 && bookUpdate > 0;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
