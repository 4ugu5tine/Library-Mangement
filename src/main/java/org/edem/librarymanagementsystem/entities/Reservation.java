package org.edem.librarymanagementsystem.entities;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int userId;
    private int bookId;
    private LocalDate date;

    public Reservation(int reservationId, int userId, int bookId, LocalDate date) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.bookId = bookId;
        this.date = date;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

