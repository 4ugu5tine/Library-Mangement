package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class TransactionService {

    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "work";


    public static LinkedList<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transaction";
        LinkedList<Transaction> transactions = new LinkedList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int transactionId = rs.getInt("transactionId");
                int bookId = rs.getInt("bookId");
                int userId = rs.getInt("userId");
                LocalDate borrowDate = rs.getDate("borrowDate").toLocalDate();
                LocalDate returnDate =  rs.getDate("returnDate").toLocalDate();
                boolean isReturned = rs.getBoolean("isReturned");

                transactions.add(new Transaction(transactionId,bookId,userId,borrowDate,returnDate,isReturned));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static Transaction borrowBook(int bookId, int userId) {
        String borrowSql = "INSERT INTO transaction (bookId, userId, borrowDate, isReturned) VALUES (?, ?, CURRENT_DATE, false)";
        String updateBookSql = """
    UPDATE books 
    SET copies = copies - 1, 
        isAvailable = CASE WHEN copies - 1 > 0 THEN TRUE ELSE FALSE END 
    WHERE bookId = ? AND copies > 0
    """;

        Transaction transaction = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);

            // Insert new transaction
            try (PreparedStatement statement = connection.prepareStatement(borrowSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, bookId);
                statement.setInt(2, userId);
                statement.setDate(3, Date.valueOf(LocalDate.now())); // Current date
                statement.setBoolean(4, false);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Get the generated transactionId
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int transactionId = generatedKeys.getInt(1);

                        // Create the transaction object
                        transaction = new Transaction(transactionId, bookId, userId, LocalDate.now(), null, false);
                    }
                }
            }

            // Update the book's availability
            try (PreparedStatement statement = connection.prepareStatement(updateBookSql)) {
                statement.setInt(1, bookId);
                statement.executeUpdate();
            }

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }


    public static Transaction returnBook(int transactionId, int bookId) {
        String returnSql = "UPDATE transactions SET isReturned = TRUE, returnDate = CURRENT_DATE WHERE transactionId = ?";
        String updateBookQuery = """
        UPDATE books 
        SET copies = copies + 1, 
            isAvailable = TRUE 
        WHERE bookId = ?
    """;

        Transaction transaction = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Prepare statements
            try (PreparedStatement transactionStatement = connection.prepareStatement(returnSql);
                 PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery)) {

                transactionStatement.setInt(1, transactionId);

                int transactionUpdate = transactionStatement.executeUpdate();
                bookStatement.setInt(1, bookId);
                int bookUpdate = bookStatement.executeUpdate();

                if (transactionUpdate > 0 && bookUpdate > 0) {
                    // Commit the transaction
                    connection.commit();

                    // Retrieve the transaction details
                    String getTransactionDetails = "SELECT * FROM transactions WHERE transactionId = ?";
                    try (PreparedStatement ps = connection.prepareStatement(getTransactionDetails)) {
                        ps.setInt(1, transactionId);
                        ResultSet resultSet = ps.executeQuery();

                        if (resultSet.next()) {
                            int transactionIdFetched = resultSet.getInt("transactionId");
                            int bookIdFetched = resultSet.getInt("bookId");
                            int userIdFetched = resultSet.getInt("userId");
                            LocalDate borrowDate = resultSet.getDate("borrowDate").toLocalDate();
                            LocalDate returnDate = resultSet.getDate("returnDate") != null ? resultSet.getDate("returnDate").toLocalDate() : null;
                            boolean isReturned = resultSet.getBoolean("isReturned");

                            // Create the transaction object
                            transaction = new Transaction(transactionIdFetched, bookIdFetched, userIdFetched, borrowDate, returnDate, isReturned);
                        }
                    }

                    return transaction;

                } else {
                    connection.rollback();
                    return null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
