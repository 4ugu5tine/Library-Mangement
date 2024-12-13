package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import static java.sql.JDBCType.NULL;

public class TransactionService {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public  LinkedList<Transaction> getAllTransactions() {
        String sql = "SELECT * FROM transaction";
        LinkedList<Transaction> transactions = new LinkedList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int transactionId = rs.getInt("transactionid");
                int bookId = rs.getInt("bookid");
                int userId = rs.getInt("userid");
                LocalDate borrowDate = rs.getDate("borrowdate").toLocalDate();
                LocalDate returnDate =  rs.getDate("returndate") == null ?  null : rs.getDate("returndate").toLocalDate();
                boolean isReturned = rs.getBoolean("isreturned");

                transactions.add(new Transaction(transactionId,bookId,userId,borrowDate,returnDate,isReturned));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public Transaction borrowBook(int bookId, int userId) {
        String borrowSql = "INSERT INTO transaction (bookId, userId, borrowDate, isReturned) VALUES (?, ?, CURRENT_DATE, false)";
        String updateBookSql = """
            UPDATE books 
            SET copies = copies - 1, 
                isAvailable = CASE WHEN copies - 1 > 0 THEN TRUE ELSE FALSE END 
            WHERE bookId = ? AND copies > 0
            """;

        Transaction transaction = null;

        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(borrowSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, bookId);
                statement.setInt(2, userId);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int transactionId = generatedKeys.getInt(1);

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


    public Transaction returnBook(int transactionId, int bookId) {
        String returnSql = "UPDATE transaction SET isReturned = TRUE, returnDate = CURRENT_DATE WHERE transactionId = ?";
        String updateBookQuery = """
        UPDATE books 
        SET copies = copies + 1, 
            isAvailable = TRUE 
        WHERE bookId = ?
    """;

        Transaction transaction = null;

        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Prepare statements
            try (PreparedStatement transactionStatement = connection.prepareStatement(returnSql);
                 PreparedStatement bookStatement = connection.prepareStatement(updateBookQuery)) {

                transactionStatement.setInt(1, transactionId);

                int transactionUpdate = transactionStatement.executeUpdate();
                bookStatement.setInt(1, bookId);
                int bookUpdate = bookStatement.executeUpdate();

                if (transactionUpdate > 0 && bookUpdate > 0) {
                    connection.commit();

                    String getTransactionDetails = "SELECT * FROM transaction WHERE transactionId = ?";
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
