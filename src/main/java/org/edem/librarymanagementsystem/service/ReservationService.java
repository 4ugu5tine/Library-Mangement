package org.edem.librarymanagementsystem.service;

import java.sql.*;
import java.time.LocalDate;

public class ReservationService {
    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "work";


    public static void createReservation(int userId, int bookId, Date date){
        String sql = """
                INSERT INTO reservation (userId, bookId, date)
                VALUES (?,?,?)
        """;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();
            System.out.println("Reservation created");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
