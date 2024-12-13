package org.edem.librarymanagementsystem.service;


import org.edem.librarymanagementsystem.entities.User;
import org.edem.librarymanagementsystem.utils.DatabaseConnection;

import java.sql.*;
import java.util.LinkedList;



public class UserService {
    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public UserService(DatabaseConnection connection){
        this.databaseConnection = connection;
    }

    public UserService() {
    }

    public boolean createLibrarian(String name, String email, String phone, String address, String password) {
        String sql = """
            INSERT INTO users (name, email, phone, address, password, accountType) 
            VALUES (?, ?, ?, ?,'Librarian', 0)
        """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setString(5, password);

            stmt.executeUpdate();
            System.out.println("Librarian created successfully");
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createPatron(String name, String email, String phone, String address) {
        String sql = """
            INSERT INTO users (name, email, phone, address, accountType, borrowedBooks)
            VALUES (?, ?, ?, ?, 'Patron', 0)
        """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedList<User> findAllPatrons() {
        String sql = "SELECT * FROM users WHERE accountType = 'Patron'";
        LinkedList<User> users = new LinkedList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("userid");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String accountType = rs.getString("accountType");
                int borrowedBooks = rs.getInt("borrowedBooks");

                User user = new User(userId, name, email, phone, address, accountType, borrowedBooks);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User findUserById(int userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        User user = null;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String accountType = rs.getString("accountType");
                int borrowedBooks = rs.getInt("borrowedBooks");

                user = new User(userId, name, email, phone, address, accountType, borrowedBooks);
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE userId = ?";

        try (Connection conn = databaseConnection.getConnection();
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
    public LinkedList<User> findUsersByName(String name) {
        String sql = "SELECT * FROM users WHERE name ILIKE ?";
        LinkedList<User> users = new LinkedList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String userName = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String accountType = rs.getString("accountType");
                int borrowedBooks = rs.getInt("borrowedBooks");

                users.add(new User(userId, userName, email, phone, address, accountType, borrowedBooks));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }



    public boolean login(String email, String password) {
        String sql = """
    SELECT userid FROM users 
    WHERE email = ? 
    AND password = ? 
    AND accounttype = 'Librarian'
    """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
