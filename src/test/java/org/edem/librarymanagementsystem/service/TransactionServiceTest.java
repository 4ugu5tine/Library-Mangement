package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Transaction;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {


    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private PreparedStatement mockBookStatement;

    @Mock
    private PreparedStatement mockDetailsStatement;

    @Mock
    private PreparedStatement mockTransactionStatement;



    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() throws SQLException {
//        Mockito.reset(mockResultSet, mockPreparedStatement, mockConnection);

        transactionService = new TransactionService(mockDatabaseConnection);
        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }


    @Test
    void getAllTransactions() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(mockResultSet.getInt("transactionid")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getInt("bookid")).thenReturn(101).thenReturn(102);
        when(mockResultSet.getInt("userid")).thenReturn(1001).thenReturn(1002);
        when(mockResultSet.getDate("borrowdate")).thenReturn(Date.valueOf("2024-01-01")).thenReturn(Date.valueOf("2024-02-01"));
        when(mockResultSet.getDate("returndate")).thenReturn(Date.valueOf("2024-01-10")).thenReturn(null);
        when(mockResultSet.getBoolean("isreturned")).thenReturn(true).thenReturn(false);

        LinkedList<Transaction> transactions = transactionService.getAllTransactions();

        assertEquals(2, transactions.size());
        assertEquals(1, transactions.get(0).getTransactionId());
        assertEquals(2, transactions.get(1).getTransactionId());
        assertEquals(101, transactions.get(0).getBookId());
        assertEquals(102, transactions.get(1).getBookId());
        assertEquals(1001, transactions.get(0).getUserId());
        assertEquals(1002, transactions.get(1).getUserId());
        assertEquals(LocalDate.of(2024, 1, 1), transactions.get(0).getBorrowDate());
        assertNull(transactions.get(1).getReturnDate());
        assertTrue(transactions.get(0).isReturned());
        assertFalse(transactions.get(1).isReturned());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Transaction> transactions_exception = transactionService.getAllTransactions();

    }

    @Test
    void borrowBook() throws SQLException {

        when(mockConnection.prepareStatement("SELECT * FROM transaction")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("transactionid")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getInt("bookid")).thenReturn(101).thenReturn(102);
        when(mockResultSet.getInt("userid")).thenReturn(1001).thenReturn(1002);
        when(mockResultSet.getDate("borrowdate"))
                .thenReturn(Date.valueOf(LocalDate.of(2023, 12, 1)))
                .thenReturn(Date.valueOf(LocalDate.of(2023, 12, 2)));
        when(mockResultSet.getDate("returndate")).thenReturn(null).thenReturn(Date.valueOf(LocalDate.of(2023, 12, 15)));
        when(mockResultSet.getBoolean("isreturned")).thenReturn(false).thenReturn(true);

        LinkedList<Transaction> transactions = transactionService.getAllTransactions();

        assertNotNull(transactions);
        assertEquals(2, transactions.size());

        Transaction t1 = transactions.get(0);
        assertEquals(1, t1.getTransactionId());
        assertEquals(101, t1.getBookId());
        assertEquals(1001, t1.getUserId());
        assertEquals(LocalDate.of(2023, 12, 1), t1.getBorrowDate());
        assertNull(t1.getReturnDate());
        assertFalse(t1.isReturned());

        Transaction t2 = transactions.get(1);
        assertEquals(2, t2.getTransactionId());
        assertEquals(102, t2.getBookId());
        assertEquals(1002, t2.getUserId());
        assertEquals(LocalDate.of(2023, 12, 2), t2.getBorrowDate());
        assertEquals(Date.valueOf(LocalDate.of(2023, 12, 15)), t2.getReturnDate());
        assertTrue(t2.isReturned());

        verify(mockDatabaseConnection).getConnection();
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();


    }

//    @Test
//    void returnBook() throws SQLException {
//        when(mockConnection.prepareStatement("UPDATE transaction SET isReturned = TRUE, returnDate = CURRENT_DATE WHERE transactionId = ?"))
//                .thenReturn(mockTransactionStatement);
//        when(mockConnection.prepareStatement("""
//            UPDATE books
//            SET copies = copies + 1,
//                isAvailable = TRUE
//            WHERE bookId = ?
//            """))
//                .thenReturn(mockBookStatement);
//        when(mockConnection.prepareStatement("SELECT * FROM transaction WHERE transactionId = ?"))
//                .thenReturn(mockDetailsStatement);
//
//        when(mockTransactionStatement.executeUpdate()).thenReturn(1);
//
//        when(mockBookStatement.executeUpdate()).thenReturn(1);
//
//        when(mockDetailsStatement.executeQuery()).thenReturn(mockResultSet);
//        when(mockResultSet.next()).thenReturn(true);
//        when(mockResultSet.getInt("transactionId")).thenReturn(1);
//        when(mockResultSet.getInt("bookId")).thenReturn(101);
//        when(mockResultSet.getInt("userId")).thenReturn(1001);
//        when(mockResultSet.getDate("borrowDate")).thenReturn(Date.valueOf(LocalDate.of(2023, 12, 1)));
//        when(mockResultSet.getDate("returnDate")).thenReturn(Date.valueOf(LocalDate.now()));
//        when(mockResultSet.getBoolean("isReturned")).thenReturn(true);
//
//        Transaction transaction = transactionService.returnBook(1, 101);
//
//        assertNotNull(transaction);
//        assertEquals(1, transaction.getTransactionId());
//        assertEquals(101, transaction.getBookId());
//        assertEquals(1001, transaction.getUserId());
//        assertEquals(LocalDate.of(2023, 12, 1), transaction.getBorrowDate());
//        assertEquals(Date.valueOf(LocalDate.now()), transaction.getReturnDate());
//        assertTrue(transaction.isReturned());
//
//        verify(mockDatabaseConnection).getConnection();
//        verify(mockTransactionStatement).setInt(1, 1);
//        verify(mockTransactionStatement).executeUpdate();
//        verify(mockBookStatement).setInt(1, 101);
//        verify(mockBookStatement).executeUpdate();
//        verify(mockDetailsStatement).setInt(1, 1);
//        verify(mockDetailsStatement).executeQuery();
//
//    }
}