package org.edem.librarymanagementsystem.service;

import org.edem.librarymanagementsystem.entities.Reservation;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class ReservationService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public ReservationService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public ReservationService() {
    }

    public void createReservation(int userId, int bookId, Date date) {
        String sql = """
                INSERT INTO reservation (userId, bookId, date)
                VALUES (?,?,?)
        """;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setDate(3, date);

            stmt.executeUpdate();
            System.out.println("Reservation created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public  LinkedList<Reservation> getAllReservations () {
            String sql = "SELECT  * FROM reservation ORDER BY date ASC";
            LinkedList<Reservation> reservations = new LinkedList<>();

            try (Connection conn = databaseConnection.getConnection();
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
