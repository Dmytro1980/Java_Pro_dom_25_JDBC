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
        try {
            InputStream in = Files.newInputStream(Paths.get("database.properties"));
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
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
