package db;

import bo.Product;
import bo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends User{
    private UserDB(int id, String username, String password) {
        super(id, username, password);
    }
    public static User searchByUsername(String username) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username); // Sätt användarnamnet i frågan
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserDB(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    public static User searchById(int id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserDB(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    public static boolean addItem(int userId, int itemId) {
        User user = UserDB.searchById(userId);
        Product item = ProductDB.searchById(itemId);
        if (user == null || item == null) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatementCheck = null;
        PreparedStatement preparedStatementInsert = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            String sqlCheck = "SELECT COUNT(*) FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementCheck = connection.prepareStatement(sqlCheck);
            preparedStatementCheck.setInt(1, userId);
            preparedStatementCheck.setInt(2, itemId);
            resultSet = preparedStatementCheck.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return false;
            }
            String sqlInsert = "INSERT INTO user_item (user_id, item_id) VALUES (?, ?)";
            preparedStatementInsert = connection.prepareStatement(sqlInsert);
            preparedStatementInsert.setInt(1, userId);
            preparedStatementInsert.setInt(2, itemId);
            preparedStatementInsert.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatementCheck != null) preparedStatementCheck.close();
                if (preparedStatementInsert != null) preparedStatementInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean removeItem(int userId, int itemId) {
        User user = UserDB.searchById(userId);
        Product item = ProductDB.searchById(itemId);
        if (user == null || item == null) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatementCheck = null;
        PreparedStatement preparedStatementDelete = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            String sqlCheck = "SELECT COUNT(*) FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementCheck = connection.prepareStatement(sqlCheck);
            preparedStatementCheck.setInt(1, userId);
            preparedStatementCheck.setInt(2, itemId);
            resultSet = preparedStatementCheck.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                return false;
            }
            String sqlDelete = "DELETE FROM user_item WHERE user_id = ? AND item_id = ?";
            preparedStatementDelete = connection.prepareStatement(sqlDelete);
            preparedStatementDelete.setInt(1, userId);
            preparedStatementDelete.setInt(2, itemId);
            preparedStatementDelete.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatementCheck != null) preparedStatementCheck.close();
                if (preparedStatementDelete != null) preparedStatementDelete.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}