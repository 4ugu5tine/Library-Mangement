package org.edem.librarymanagementsystem.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DatabaseConnectionTest {

    @Test
    void getConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class);){
            mockedDriverManager.when(()-> DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            assertNotNull(connection);
            assertEquals(mockConnection, connection);
            mockedDriverManager.verify(() -> DriverManager.getConnection(anyString(), anyString(), anyString()), times(1));

        }
    }
}