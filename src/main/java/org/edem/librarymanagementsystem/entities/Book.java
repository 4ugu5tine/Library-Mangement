package org.edem.librarymanagementsystem.entities;

import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private int yearPublished;
    private boolean isAvailable;
    private int genreId;
    private int copies;


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public Book(int bookId, String title, String author, String publisher, int yearPublished, boolean isAvailable, int genreId, int copies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.isAvailable = isAvailable;
        this.genreId = genreId;
        this.copies = copies;
    }

    // CRUD Operations


    // Other CRUD methods...
}

