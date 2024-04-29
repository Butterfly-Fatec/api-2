package meuapp.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import meuapp.config.PropertiesLoader;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;


public class LMStudioService {
    private static final String URL = "http://localhost:1234/v1/";
    private final JTextField input;
    private String sqlQuery;
    private final DataBaseService dataBaseService;

    public LMStudioService(JTextField input, DataBaseService dataBaseService) {
        this.input = input;
        this.dataBaseService= dataBaseService;
    }


    public void connectionLMStudio() throws IOException {
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl(URL)
                .modelName("localModel")
                .temperature(0.7)
                .build();

        String instructions = "### Task\n" +
                "Generate a SQL query to answer [QUESTION][" + this.input.getText() + "][/QUESTION]\n\n" +

                "### Database Schema\n" +
                "The query will run on a database with the following schema:\n" +
                "[" + dataBaseService.getDataSchemas() + "]\n\n" +

                "### Answer SQL Query\n" +
                "Given the database schema, here is the SQL query that answers [QUESTION][" + this.input.getText() + "][/QUESTION]\n" +

                "[SQL] Your SQL query goes here [/SQL], no explanation and no formatted, use only data of schema";

        this.sqlQuery = model.generate(instructions);
    }


    public String resultSQL(String selectedSchema) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadProperties();
        String URL = propertiesLoader.getURL();
        String USER = propertiesLoader.getUSER();
        String PASSWORD = propertiesLoader.getPASSWORD();

        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(URL+"/"+selectedSchema,USER,PASSWORD);
            PreparedStatement statement = connection.prepareStatement(this.sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(columnName);
                    stringBuilder.append(value).append(" ");
                }
                stringBuilder.append("<br>");
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error filter result: " + e.getMessage());
        }
        return stringBuilder.toString();
    }
}