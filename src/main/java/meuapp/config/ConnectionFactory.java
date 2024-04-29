package meuapp.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    protected static final String URL;
    protected static final String USER;
    protected static final String PASSWORD;

    static {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();
        URL = propertiesLoader.getURL();
        USER = propertiesLoader.getUSER();
        PASSWORD = propertiesLoader.getPASSWORD();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

