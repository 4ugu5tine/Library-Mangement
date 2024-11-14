-- src/main/resources/library_schema.sql

-- Create table for Genres
CREATE TABLE IF NOT EXISTS genre (
                                     genreId SERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    description TEXT
    );

-- Create table for Books
CREATE TABLE IF NOT EXISTS books (
                                     bookId SERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    genreId INTEGER NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    yearPublished INT NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (genreId) REFERENCES genre(genreId)
    );

-- Create table for Patrons
CREATE TABLE IF NOT EXISTS patron (
                                      patronId SERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(15),
    borrowedBooks INTEGER DEFAULT 0
    );

-- Create table for Librarians
CREATE TABLE IF NOT EXISTS librarian (
                                         librarianId SERIAL PRIMARY KEY,
                                         username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
    );

-- Create table for Transactions
CREATE TABLE IF NOT EXISTS transaction (
                                           transactionId SERIAL PRIMARY KEY,
                                           bookId INTEGER NOT NULL,
                                           patronId INTEGER NOT NULL,
                                           borrowDate DATE NOT NULL,
                                           returnDate DATE,
                                           isReturned BOOLEAN DEFAULT FALSE,
                                           librarianId INTEGER NOT NULL,
                                           FOREIGN KEY (bookId) REFERENCES books(bookId),
    FOREIGN KEY (patronId) REFERENCES patron(patronId),
    FOREIGN KEY (librarianId) REFERENCES librarian(librarianId)
    );

