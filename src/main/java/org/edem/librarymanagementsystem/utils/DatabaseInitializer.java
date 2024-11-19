package org.edem.librarymanagementsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {


        private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
        private static final String USER = "postgres";
        private static final String PASSWORD = "work";

        public static void main(String[] args) {
            String createTablesSQL = """
            CREATE TABLE IF NOT EXISTS users (
                  userId SERIAL PRIMARY KEY,
                  name VARCHAR(255) NOT NULL,
                  email VARCHAR(255) UNIQUE NOT NULL,
                  phone VARCHAR(15),
                  address VARCHAR(255),
                  password VARCHAR(255) NOT NULL,
                  accountType VARCHAR(50) NOT NULL CHECK (accountType IN ('Librarian', 'Patron')),
                  borrowedBooks INTEGER DEFAULT 0 
              );
              
              CREATE TABLE IF NOT EXISTS books (
                  bookId SERIAL PRIMARY KEY,
                  title VARCHAR(255) NOT NULL,
                  genre VARCHAR NOT NULL,
                  author VARCHAR(255) NOT NULL,
                  publisher VARCHAR(255) NOT NULL,
                  yearPublished INT NOT NULL,
                  isAvailable BOOLEAN DEFAULT TRUE,
                  copies INTEGER NOT NULL
              );
              
       
              CREATE TABLE IF NOT EXISTS genre (
                  name VARCHAR(100) PRIMARY KEY,
                  description TEXT
              );
              
              CREATE TABLE IF NOT EXISTS reservation (
                  reservationId SERIAL PRIMARY KEY,
                  userId INTEGER NOT NULL REFERENCES users(userId), 
                  bookId INTEGER NOT NULL REFERENCES books(bookId), 
                  date DATE NOT NULL 
              );
              
              CREATE TABLE IF NOT EXISTS transaction (
                  transactionId SERIAL PRIMARY KEY,
                  bookId INTEGER NOT NULL REFERENCES books(bookId),
                  userId INTEGER NOT NULL REFERENCES users(userId), 
                  borrowDate DATE NOT NULL,
                  returnDate DATE,
                  isReturned BOOLEAN DEFAULT FALSE
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

