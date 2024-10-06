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
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM item");
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
        Connection connection = null;
        PreparedStatement preparedStatementUserProducts = null;
        ResultSet resultsetUserProducts = null;
        PreparedStatement statementProducts = null;
        ResultSet resultsetProducts = null;
        try {
            connection = DBconnection.getConnection();
            String sqlUserItems = "SELECT item_id FROM user_item WHERE user_id = ?";
            preparedStatementUserProducts = connection.prepareStatement(sqlUserItems);
            preparedStatementUserProducts.setInt(1, userId);
            resultsetUserProducts = preparedStatementUserProducts.executeQuery();
            while (resultsetUserProducts.next()) {
                int itemId = resultsetUserProducts.getInt("item_id");
                String sqlItem = "SELECT * FROM Item WHERE id = ?";
                statementProducts = connection.prepareStatement(sqlItem);
                statementProducts.setInt(1, itemId);
                resultsetProducts = statementProducts.executeQuery();
                if (resultsetProducts.next()) {
                    String itemName = resultsetProducts.getString("name");
                    String description = resultsetProducts.getString("description");
                    int price = resultsetProducts.getInt("price");
                    Product item = new ProductDB(itemId, itemName, description, price);
                    products.add(item);
                }
                resultsetProducts.close();
                statementProducts.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultsetUserProducts != null) resultsetUserProducts.close();
                if (preparedStatementUserProducts != null) preparedStatementUserProducts.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
    public static Product searchById(int id) {
        Product product = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBconnection.getConnection();
            String sql = "SELECT * FROM item WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new ProductDB(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("price"));
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
        return product;
    }
}