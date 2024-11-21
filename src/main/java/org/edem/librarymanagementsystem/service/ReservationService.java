package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Reservation;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class ReservationService {
    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "work";


    public static void createReservation(int userId, int bookId, LocalDate date) {
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

        public static LinkedList<Reservation> getAllReservations () {
            String sql = "SELECT  * FROM reservation ORDER BY date ASC";
            LinkedList<Reservation> reservations = new LinkedList<>();

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    int reservationId = rs.getInt("reservationid");
                    int bookId = rs.getInt("bookid");
                    int userId = rs.getInt("userid");
                    LocalDate date = rs.getDate("date").toLocalDate();

                    reservations.add(new Reservation(reservationId, userId,bookId,date));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return reservations;
        }

}
