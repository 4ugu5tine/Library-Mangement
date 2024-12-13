package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Book;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {


    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() throws SQLException {
        bookService = new BookService(mockDatabaseConnection);
        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testAddBook() throws SQLException {

        bookService.addBook("Effective Java", "Joshua Bloch", "Addison-Wesley", 2018, "Programming", 5);

        verify(mockConnection).prepareStatement("INSERT INTO books (title, author, publisher, yearPublished,isAvailable, genre,copies ) VALUES (?, ?, ?, ?, ?, ?, ?)");
        verify(mockPreparedStatement).setString(1, "Effective Java");
        verify(mockPreparedStatement).setString(2, "Joshua Bloch");
        verify(mockPreparedStatement).setString(3, "Addison-Wesley");
        verify(mockPreparedStatement).setInt(4, 2018);
        verify(mockPreparedStatement).setBoolean(5, true);
        verify(mockPreparedStatement).setString(6, "Programming");
        verify(mockPreparedStatement).setInt(7, 5);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void getBookById() {

    }

    @Test
    void searchBooks() {
    }

    @Test
    void getAllBooks() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("bookId")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("title")).thenReturn("Book One").thenReturn("Book Two");
        when(mockResultSet.getString("author")).thenReturn("Author One").thenReturn("Author Two");
        when(mockResultSet.getString("publisher")).thenReturn("Publisher One").thenReturn("Publisher Two");
        when(mockResultSet.getInt("yearPublished")).thenReturn(2020).thenReturn(2021);
        when(mockResultSet.getBoolean("isAvailable")).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("genre")).thenReturn("Fiction").thenReturn("Non-Fiction");
        when(mockResultSet.getInt("copies")).thenReturn(5).thenReturn(3);

        LinkedList<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("Book One", books.get(0).getTitle());
        assertEquals("Book Two", books.get(1).getTitle());
        assertEquals("Author One", books.get(0).getAuthor());
        assertEquals("Non-Fiction", books.get(1).getGenre());
    }

    @Test
    void deleteBook() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        int bookId = 1;

        bookService.deleteBook(bookId);

        verify(mockConnection).prepareStatement("DELETE FROM books WHERE bookId = ?");
        verify(mockPreparedStatement).setInt(1, bookId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void updateBook() throws SQLException {

        bookService.updateBook(1, "Effective Java", "Joshua Bloch", 2018, "Programming", 5);

        verify(mockConnection).prepareStatement("""
        UPDATE books
        SET title = ?, author = ?, yearPublished = ?, genre = ?, copies = ?
        WHERE bookId = ?
    """);
        verify(mockPreparedStatement).setString(1, "Effective Java");
        verify(mockPreparedStatement).setString(2, "Joshua Bloch");
        verify(mockPreparedStatement).setInt(3, 2018);
        verify(mockPreparedStatement).setString(4, "Programming");
        verify(mockPreparedStatement).setInt(5, 5);
        verify(mockPreparedStatement).setInt(6, 1);
        verify(mockPreparedStatement).executeUpdate();
    }
}