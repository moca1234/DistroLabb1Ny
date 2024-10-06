package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    private static DBconnection instance = null;
    private Connection connection = null;


    private static DBconnection getInstance() {
        if (instance == null) {
            instance = new DBconnection();
        }
        return instance;
    }


    private DBconnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/data",
                    "root",
                    "jaraara3");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return getInstance().connection;
    }
}
