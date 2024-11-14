package org.edem.librarymanagementsystem.entities;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private int bookId;
    private int patronId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private int librarianId;

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Transaction(int transactionId, int bookId, int patronId, LocalDate borrowDate, LocalDate returnDate, boolean isReturned, int librarianId) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.patronId = patronId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.librarianId = librarianId;
    }

    // CRUD Operations
    public static void addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (bookId, patronId, borrowDate, returnDate, isReturned, librarianId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getBookId());
            stmt.setInt(2, transaction.getPatronId());
            stmt.setDate(3, Date.valueOf(transaction.getBorrowDate()));
            stmt.setDate(4, transaction.getReturnDate() == null ? null : Date.valueOf(transaction.getReturnDate()));
            stmt.setBoolean(5, transaction.isReturned());
            stmt.setInt(6, transaction.getLibrarianId());
            stmt.executeUpdate();
        }
    }

    // Other CRUD methods...
}
