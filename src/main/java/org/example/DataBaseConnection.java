package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {

    public static Connection getConnection() {            // - повертає нове з'єднання з БД
        Properties properties = new Properties();
        String[] propArray = new String[3];
        try {
            InputStream in = Files.newInputStream(Paths.get("database.properties"));
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        propArray[0]=properties.getProperty("url");       // url
        propArray[1]=properties.getProperty("username");  // username
        propArray[2]=properties.getProperty("password"); // password

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(propArray[0], propArray[1], propArray[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection connection) {      // - закриває передане з'єднання
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
