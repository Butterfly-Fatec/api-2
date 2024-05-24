package meuapp.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionDB {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try {
            Connection connection = connectionFactory.getConnection();
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");
                connection.close();
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão: " + e.getMessage());
        }
    }
}