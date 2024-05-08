package meuapp.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class ConnectionFactory {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    private String URL;
    private String USER;
    private String PASSWORD;

    public ConnectionFactory(){
        loadProperties();
    }

    public void loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
            this.URL = properties.getProperty("URL");
            this.USER = properties.getProperty("USER");
            this.PASSWORD = properties.getProperty("PASSWORD");
        } catch (IOException e) {
            System.out.println("Error PropertiesLoader: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.URL,this.USER, this.PASSWORD);
    }

    public String getURL() {
        return this.URL;
    }

    public String getUSER() {
        return this.USER;
    }

    public String getPASSWORD() {
        return this.PASSWORD;
    }
}

