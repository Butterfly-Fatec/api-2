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
    private final ConnectionFactory connectionFactory;
    private static final String URL = "http://localhost:1234/v1/";
    private final String input;
    private String output;
    private String querySQL;
    private final DataBaseService dataBaseService;

    public LMStudioService(String input, DataBaseService dataBaseService) {
        this.input = input;
        this.output = "";
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
                "Generate a SQL query to answer [QUESTION]{" + input + "}[/QUESTION]\n\n" +

                "Instructions\n" +
                "If you cannot answer the question with the available database schema, return 'Eu não sei'\n\n" +

                "Database Schema\n" +
                "The query will run on a database with the following schema: {" + dataBaseService.getDataSchemas()
                + "}\n\n" +

                "Answer\n" +
                "Given the database schema, here is the SQL query that answers [QUESTION]{" + input + "}[/QUESTION]\n" +
                "[SQL] Your SQL query goes here[/SQL]";

        this.querySQL = model.generate(instructions);
    }

    public void resultSQL(String selectedSchema) {
        try {
            Connection connection = DriverManager.getConnection(connectionFactory.getURL() + "/" + selectedSchema,
                    connectionFactory.getUSER(), connectionFactory.getPASSWORD());
            PreparedStatement statement = connection.prepareStatement(this.querySQL);
            ResultSet resultSet = statement.executeQuery();
            DBResultFormatter formatter = new DBResultFormatter();
            this.output = formatter.FormatResult(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error filter result: " + e.getMessage());
        }
    }

    public String getOutput() {
        return output;
    }
}