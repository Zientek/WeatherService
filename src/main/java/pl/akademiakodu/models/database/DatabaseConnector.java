package pl.akademiakodu.models.database;

import pl.akademiakodu.models.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {
    
    private static DatabaseConnector instance = new DatabaseConnector();
    public static DatabaseConnector getInstance() {
        return instance;
    }

    private Connection connection; // klasa przechowujace polaczenie
    
    private DatabaseConnector(){
        connect();
    }

    private void connect() {
        try {
                Class.forName("com.mysql.jdbc.Driver"); //refleksja
            try {
                connection = DriverManager.getConnection(Config.MYSQL_LINK,Config.MYSQL_LOGIN,Config.MYSQL_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        System.out.println("It works!");
    }

    public PreparedStatement createStatment(String SQL){
        try {
            return connection.prepareStatement(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


