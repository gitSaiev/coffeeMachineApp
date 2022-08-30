package ru.saiev.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionConfig {
    private final String DB_PROPERTIES = "src\\main\\resources\\database\\db.properties";

    public Connection getDbConnection() throws SQLException, IOException {
        Properties property = new Properties();
        property.load(Files.newInputStream(Paths.get(DB_PROPERTIES)));
        Connection db = DriverManager.getConnection(property.getProperty("url"), property);
        return db;
    }
}
