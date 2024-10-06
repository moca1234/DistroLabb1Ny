package db;

import bo.Product;
import bo.User;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB extends Product {
    private ProductDB(int id, String name, String description, int price) {
        super(id, name, description, price);
    }
    public static ArrayList<Product> listAllItems() {
        ArrayList<Product> products = new ArrayList<>();
        // Deklarera resurser för stängning
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Anslutning till databasen
            connection = DBconnection.getConnection();
            // Skapa en Statement
            statement = connection.createStatement();
            // Utför frågan
            resultSet = statement.executeQuery("SELECT * FROM item");
            // Bearbeta resultatet
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                products.add(new ProductDB(id, name, description, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
    public static ArrayList<Product> searchItemByUsername(String username) {
        ArrayList<Product> products = new ArrayList<>();
        User user = UserDB.searchByUsername(username);
        if (user == null) {
            return products;
        }
        int userId = user.getId();
        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatementUserProducts = null;
        ResultSet resultsetUserProducts = null;
        PreparedStatement statementProducts = null;
        ResultSet resultsetProducts = null;
        try {
            // Anslutning till databasen
            connection = DBconnection.getConnection();
            // Hämta alla item_id från user_item-tabellen för den specifika användaren
            String sqlUserItems = "SELECT item_id FROM user_item WHERE user_id = ?";
            preparedStatementUserProducts = connection.prepareStatement(sqlUserItems);
            preparedStatementUserProducts.setInt(1, userId);
            resultsetUserProducts = preparedStatementUserProducts.executeQuery();
            // Gå igenom varje item_id som hittas för userId
            while (resultsetUserProducts.next()) {
                int itemId = resultsetUserProducts.getInt("item_id");
                // Hämta motsvarande Item från Item-tabellen
                String sqlItem = "SELECT * FROM Item WHERE id = ?";
                statementProducts = connection.prepareStatement(sqlItem);
                statementProducts.setInt(1, itemId);
                resultsetProducts = statementProducts.executeQuery();
                // Om item hittas, skapa ett Item-objekt och lägg till det i listan
                if (resultsetProducts.next()) {
                    String itemName = resultsetProducts.getString("name");
                    String description = resultsetProducts.getString("description");
                    int price = resultsetProducts.getInt("price");
                    // Skapa ett nytt Item-objekt och lägg till det i listan
                    Product item = new ProductDB(itemId, itemName, description, price);
                    products.add(item);
                }
                // Stäng resurser för item query
                resultsetProducts.close();
                statementProducts.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Stäng resurser för user_item query
            try {
                if (resultsetUserProducts != null) resultsetUserProducts.close();
                if (preparedStatementUserProducts != null) preparedStatementUserProducts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Returnera listan med items
        return products;
    }
    public static Product searchById(int id) {
        Product product = null; // För att hålla användarobjektet
        // Deklarera resurser för stängning
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Anslutning till databasen
            connection = DBconnection.getConnection();
            // Förbered SQL-frågan
            String sql = "SELECT * FROM item WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Sätt användarnamnet i frågan
            // Utför frågan
            resultSet = preparedStatement.executeQuery();
            // Hämta resultatet om det finns
            if (resultSet.next()) {
                product = new ProductDB(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Stäng resurser
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return product; // Returnera användaren eller null om inte hittad
    }
}