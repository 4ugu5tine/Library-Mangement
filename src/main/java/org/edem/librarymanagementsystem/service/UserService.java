package org.edem.librarymanagementsystem.service;


import org.edem.librarymanagementsystem.entities.User;

import java.sql.*;
import java.util.LinkedList;


public class UserService {

    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "work";

    public static void createLibrarian(String name, String email, String phone, String address, String password, String accountType) {
        String sql = """
            INSERT INTO users (name, email, phone, address, password, accountType) 
            VALUES (?, ?, ?, ?,?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setString(5, password);
            stmt.setString(6, accountType);

            stmt.executeUpdate();
            System.out.println("User created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createPatron(String name, String email, String phone,String address, String accountType, int borrowedBooks) {
        String sql = """
            INSERT INTO users (name, email, phone, address, accountType, borrowedBooks)
            VALUES (?, ?, ?,?, 'Patron', 0)
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);

            stmt.executeUpdate();
            System.out.println("Patron created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<User> findAllPatrons() {
        String sql = "SELECT * FROM users WHERE accountType = 'Patron'";
        LinkedList<User> users = new LinkedList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String accountType = rs.getString("accountType");
                int borrowedBooks = rs.getInt("borrowedBooks");

                // Create a new User object and add it to the list
                User user = new User(userId, name, email, phone, address, accountType, borrowedBooks);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static String findUserById(int userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        String user = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String accountType = rs.getString("accountType");

                user = "ID: " + userId + ", Name: " + name + ", Email: " + email + ", Account Type: " + accountType;
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static String findUserByEmail(int userId) {
        String sql = "SELECT * FROM users WHERE email = ?";
        String user = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String accountType = rs.getString("accountType");

                user = "ID: " + userId + ", Name: " + name + ", Email: " + email + ", Account Type: " + accountType;
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE userId = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public  String login(String email, String password) {
//        String sql = "SELECT userId, name, FROM users WHERE email = ? AND password = ? accountType = 'Librarian' ";
//
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setString(1, email);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                int userId = rs.getInt("userId");
//                String name = rs.getString("name");
//                String accountType = rs.getString("accountType");
//
//                return "Login successful! Welcome, " + name;
//            } else {
//                return "Invalid email or password.";
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return "An error occurred during login.";
//        }
//    }

    public static User login(String email, String password) {
        String sql = """
        SELECT id, name, email, phone, address, account_type, borrowed_books, registration_date 
        FROM users 
        WHERE email = ? 
        AND password = ? 
        AND account_type = 'Librarian'
    """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);  // Note: In practice, passwords should be hashed
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("account_type"),
                        rs.getInt("borrowed_books")
                );
            }

            return null;  // No user found with these credentials

        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Database error
        }
    }
}
