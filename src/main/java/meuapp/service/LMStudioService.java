package meuapp.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import meuapp.config.ConnectionFactory;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LMStudioService {
    private final ConnectionFactory connectionFactory;
    private static final String URL = "http://localhost:1234/v1/";
    private final JTextField input;
    private String sqlQuery;
    private ArrayList<String> nameModel;
    private final DataBaseService dataBaseService;

    public LMStudioService(JTextField input, DataBaseService dataBaseService) {
        this.input = input;
        this.dataBaseService = dataBaseService;
        this.connectionFactory = new ConnectionFactory();
    }

    public void connectionLMStudio() throws IOException {
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl(URL)
                .modelName("localModel")
                .temperature(0.7)
                .build();

        String instructions = "Task\n" +
                "Generate a SQL query to answer [QUESTION]{"+this.input.getText()+"}[/QUESTION]\n\n" +

                "Instructions\n" +
                "If you cannot answer the question with the available database schema, return 'Eu não sei'\n\n" +

                "Database Schema\n" +
                "The query will run on a database with the following schema: {"+dataBaseService.getDataSchemas()+"}\n\n" +

                "Answer\n" +
                "Given the database schema, here is the SQL query that answers [QUESTION]{"+this.input.getText()+"}[/QUESTION]\n" +
                "[SQL] Your SQL query goes here[/SQL]";

        this.sqlQuery = model.generate(instructions);
    }

    public String resultSQL(String selectedSchema) {
        String result = "";
        try {
            Connection connection = DriverManager.getConnection(connectionFactory.getURL() + "/" + selectedSchema, connectionFactory.getUSER(), connectionFactory.getPASSWORD());
            PreparedStatement statement = connection.prepareStatement(this.sqlQuery);
            ResultSet resultSet = statement.executeQuery();
            DBResultFormatter formatter = new DBResultFormatter();
            result = formatter.FormatResult(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error filter result: " + e.getMessage());
        }
        return result;
    }

}