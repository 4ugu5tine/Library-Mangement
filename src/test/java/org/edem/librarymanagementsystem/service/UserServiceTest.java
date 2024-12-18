package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() throws SQLException {
        userService = new UserService(mockDatabaseConnection);
        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreatePatron() throws Exception {

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
         boolean success = userService.createPatron("John Doe", "dai@example.com", "123456789", "123 Main St");

        assertTrue(success);

    }

    @Test
    void testCreateLibrarian() throws Exception {

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

         boolean success = userService.createLibrarian("John Doe", "dai@example.com", "123456789", "123 Main St", "password");

        assertTrue(success);

    }


    @Test
    void testLogin() throws Exception {

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);

        boolean success = userService.login("dai@example.com", "password");

        assertTrue(success);

        when(mockResultSet.next()).thenReturn(false);

        success = userService.login("wrong@example.com", "wrongpassword");

        assertFalse(success);

    }


    @Test
    void testDeleteUser() throws SQLException{
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int userId = 1;
        userService.deleteUser(userId);

        verify(mockConnection).prepareStatement("DELETE FROM users WHERE userId = ?");
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testFindAllPatrons() throws Exception{
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("userid")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("John Doe").thenReturn("Jane Smith");
        when(mockResultSet.getString("email")).thenReturn("john@example.com").thenReturn("jane@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890").thenReturn("0987654321");
        when(mockResultSet.getString("address")).thenReturn("123 Main St").thenReturn("456 Elm St");
        when(mockResultSet.getString("accountType")).thenReturn("Patron").thenReturn("Patron");
        when(mockResultSet.getInt("borrowedBooks")).thenReturn(5).thenReturn(3);

        LinkedList<User> users = userService.findAllPatrons();

        assertEquals(2, users.size());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("Jane Smith", users.get(1).getName());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<User> users_exception = userService.findAllPatrons();
    }


    @Test
    void testFindUsersByName() throws Exception {
        String searchName = "John";

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Two users, then end
        when(mockResultSet.getInt("userId")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("John Doe").thenReturn("Johnny Appleseed");
        when(mockResultSet.getString("email")).thenReturn("johndoe@example.com").thenReturn("johnny@example.com");
        when(mockResultSet.getString("phone")).thenReturn("123456789").thenReturn("987654321");
        when(mockResultSet.getString("address")).thenReturn("123 Main St").thenReturn("456 Elm St");
        when(mockResultSet.getString("accountType")).thenReturn("Patron").thenReturn("Librarian");
        when(mockResultSet.getInt("borrowedBooks")).thenReturn(3).thenReturn(5);

        LinkedList<User> users = userService.findUsersByName(searchName);

        assertNotNull(users);
        assertEquals(2, users.size());

        assertEquals(1, users.get(0).getUserid());
        assertEquals("John Doe", users.get(0).getName());
        assertEquals("johndoe@example.com", users.get(0).getEmail());
        assertEquals("123456789", users.get(0).getPhone());
        assertEquals("123 Main St", users.get(0).getAddress());
        assertEquals("Patron", users.get(0).getAccountType());
        assertEquals(3, users.get(0).getBorrowedBooks());

        assertEquals(2, users.get(1).getUserid());
        assertEquals("Johnny Appleseed", users.get(1).getName());
        assertEquals("johnny@example.com", users.get(1).getEmail());
        assertEquals("987654321", users.get(1).getPhone());
        assertEquals("456 Elm St", users.get(1).getAddress());
        assertEquals("Librarian", users.get(1).getAccountType());
        assertEquals(5, users.get(1).getBorrowedBooks());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<User > users_exception = userService.findUsersByName(searchName);
    }

    @Test
    void testFindUserById() throws Exception{

        int userId = 1;

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);


        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("johndoe@example.com");
        when(mockResultSet.getString("phone")).thenReturn("123456789");
        when(mockResultSet.getString("address")).thenReturn("123 Main St");
        when(mockResultSet.getString("accountType")).thenReturn("Librarian");
        when(mockResultSet.getInt("borrowedBooks")).thenReturn(5);

        User user = userService.findUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getUserid());
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123456789", user.getPhone());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("Librarian", user.getAccountType());
        assertEquals(5, user.getBorrowedBooks());


        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        User user_exception = userService.findUserById(userId);

    }

}