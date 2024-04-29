package meuapp.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    private String URL;
    private String USER;
    private String PASSWORD;

    public void loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
            this.URL = properties.getProperty("URL");
            this.USER = properties.getProperty("USER");
            this.PASSWORD = properties.getProperty("PASSWORD");
        } catch (IOException e) {
            System.out.println("Erro PropertiesLoader: " + e.getMessage());
        }
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

