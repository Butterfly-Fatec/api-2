package meuapp.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import meuapp.config.ConnectionFactory;

public class LMStudioService {

    public static String sendLMStudioQuery(String question, DataBaseService dataBaseService) throws IOException {
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl("http://localhost:1234/v1/")
                .modelName("loaded")
                .temperature(0.7)
                .build();

        String instructions = "Task\n" +
                "Generate a SQL query to answer [QUESTION]{" + question + "}[/QUESTION]\n\n" +

                "Instructions\n" +
                "If you cannot answer the question with the available database schema, return 'Eu não sei'\n\n" +

                "Database Schema\n" +
                "The query will run on a database with the following schema: {" + dataBaseService.getDataSchemas()
                + "}\n\n" +

                "Answer\n" +
                "Given the database schema, here is the SQL query that answers [QUESTION]{" + question + "}[/QUESTION]\n" +
                "[SQL] Your SQL query goes here[/SQL]";

        return model.generate(instructions);
    }

    public static String resultSQL(String selectedSchema, String query, ConnectionFactory connectionFactory) {
        try {
            Connection connection = DriverManager.getConnection(connectionFactory.getURL() + "/" + selectedSchema,
                    connectionFactory.getUSER(), connectionFactory.getPASSWORD());
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            DBResultFormatter formatter = new DBResultFormatter();
            String result = formatter.FormatResult(resultSet);
            resultSet.close();
            statement.close();
            connection.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Error filter result: " + e.getMessage());
            return "Erro.";
        }
    }

}