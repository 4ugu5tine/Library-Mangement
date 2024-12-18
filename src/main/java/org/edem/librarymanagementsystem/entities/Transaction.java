package org.edem.librarymanagementsystem.entities;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private int bookId;
    private int userId;
    private LocalDate borrowDate;
    private Date returnDate;
    private boolean isReturned;

    public Transaction(int transactionId, int bookId, int userId, LocalDate borrowDate, Date returnDate, boolean isReturned) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
    }

    public Transaction( int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public boolean isIsReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public boolean isReturned() {
        return isReturned;
    }
}
