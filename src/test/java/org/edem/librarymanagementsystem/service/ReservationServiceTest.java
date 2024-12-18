package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Reservation;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {


    @Mock
    private DatabaseConnection mockDatabaseConnection;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private ReservationService reservationService;


    @BeforeEach
    void setUp() throws SQLException {
        reservationService = new ReservationService(mockDatabaseConnection);
        when(mockDatabaseConnection.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void createReservation() throws SQLException {

        int userId = 1;
        int bookId = 2;
        Date reservationDate = Date.valueOf(LocalDate.now());

        reservationService.createReservation(userId, bookId, reservationDate);

//        verify(mockDatabaseConnection).getConnection();
//        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, userId);
        verify(mockPreparedStatement).setInt(2, bookId);
        verify(mockPreparedStatement).setDate(3, reservationDate);
        verify(mockPreparedStatement).executeUpdate();

        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void getAllReservations() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("reservationid")).thenReturn(1, 2);
        when(mockResultSet.getInt("bookid")).thenReturn(101, 102);
        when(mockResultSet.getInt("userid")).thenReturn(201, 202);
        when(mockResultSet.getDate("date")).thenReturn(Date.valueOf(LocalDate.of(2024, 12, 18)), Date.valueOf(LocalDate.of(2024, 12, 19)));


        LinkedList<Reservation> reservations = reservationService.getAllReservations();


        assertAll("multiple asserts",
                ()-> assertEquals(2, reservations.size()),

                ()-> assertEquals(1, reservations.get(0).getReservationId()),
                ()-> assertEquals(2, reservations.get(1).getReservationId()),

                ()-> assertEquals(101, reservations.get(0).getBookId()),
                ()-> assertEquals(102, reservations.get(1).getBookId()),

                ()-> assertEquals(201, reservations.get(0).getUserId()),
                ()-> assertEquals(202, reservations.get(1).getUserId())
        );
    }
}