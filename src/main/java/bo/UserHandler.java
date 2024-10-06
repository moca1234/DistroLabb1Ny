package bo;

import db.ProductDB;
import db.UserDB;

import java.util.ArrayList;

public class UserHandler {

    public static boolean Login(String username, String password) {
        User user = UserDB.searchByUsername(username);

        if (user == null){
            return false;
        }

        return user.getPassword().equals(password);
    }

    public static ArrayList<Product> getMyProducts(String username) {
        ArrayList<Product> products = ProductDB.searchItemByUsername(username);

        return products;
    }

    public static ArrayList<Product> getAllPoducts() {
        ArrayList<Product> products = ProductDB.listAllItems();

        return products;
    }

    public static boolean addProduct(int userId, int productId) {
        if (UserDB.addItem(userId, productId)){
            User user = UserDB.searchById(userId);
            Product product = ProductDB.searchById(productId);
            user.addProduct(product);
            return true;
        }

        return false;
    }

        public static boolean removeProducts(int userId, int productId) {
            if (UserDB.removeItem(userId, productId)){
                User user = UserDB.searchById(userId);
                Product product = ProductDB.searchById(productId);
                user.removeProduct(product);
                return true;
            }

            return false;
        }

        public static User getUserByUsername(String username) {
            return UserDB.searchByUsername(username);
        }
    }