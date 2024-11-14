package org.edem.librarymanagementsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {


        private static final String URL = "jdbc:postgresql://localhost:5432/your_database_name";
        private static final String USER = "your_username";
        private static final String PASSWORD = "your_password";

        public static void main(String[] args) {
            String createTablesSQL = """
            CREATE TABLE IF NOT EXISTS books (
                bookId SERIAL PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                genreId INTEGER NOT NULL,
                author VARCHAR(255) NOT NULL,
                publisher VARCHAR(255) NOT NULL,
                yearPublished INT NOT NULL,
                isAvailable BOOLEAN DEFAULT TRUE
            );

            CREATE TABLE IF NOT EXISTS genre (
                genreId SERIAL PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                description TEXT
            );

            CREATE TABLE IF NOT EXISTS patron (
                patronId SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                phone VARCHAR(15),
                borrowedBooks INTEGER DEFAULT 0
            );

            CREATE TABLE IF NOT EXISTS librarian (
                librarianId SERIAL PRIMARY KEY,
                username VARCHAR(255) UNIQUE NOT NULL,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS transaction (
                transactionId SERIAL PRIMARY KEY,
                bookId INTEGER NOT NULL REFERENCES books(bookId),
                patronId INTEGER NOT NULL REFERENCES patron(patronId),
                borrowDate DATE NOT NULL,
                returnDate DATE,
                isReturned BOOLEAN DEFAULT FALSE,
                librarianId INTEGER NOT NULL REFERENCES librarian(librarianId)
            );
        """;

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createTablesSQL);
                System.out.println("Tables created successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


}

