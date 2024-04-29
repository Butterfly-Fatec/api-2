package meuapp.config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnectionBD {
    public static void main(String[] args) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            if (connection != null) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão: " + e.getMessage());
        }
    }
}
