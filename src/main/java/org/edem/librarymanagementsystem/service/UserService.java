package org.edem.librarymanagementsystem.service;

import java.sql.*;
import java.util.LinkedList;


public class UserService {

    private static final String URL = "jdbc:postgresql://localhost:5432/libraryDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password";

    public static void createUser(String name, String email, String phone, String password, String accountType) {
        String sql = """
            INSERT INTO users (name, email, phone, password, accountType) 
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, password);
            stmt.setString(5, accountType);

            stmt.executeUpdate();
            System.out.println("User created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> fetchAllUsers() {
        String sql = "SELECT * FROM users";
        LinkedList<String> users = new LinkedList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String accountType = rs.getString("accountType");

                String user = "ID: " + userId + ", Name: " + name + ", Email: " + email + ", Account Type: " + accountType;
                users.add(user); // Add user info to the LinkedList
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static String fetchUserById(int userId) {
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

    public static String login(String email, String password) {
        String sql = "SELECT userId, name, accountType FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String accountType = rs.getString("accountType");

                return "Login successful! Welcome, " + name + " (" + accountType + ")";
            } else {
                return "Invalid email or password.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "An error occurred during login.";
        }
    }
}
