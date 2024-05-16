package meuapp.service;

import java.util.ArrayList;
import java.sql.*;

import meuapp.config.ConnectionFactory;

public class DataBaseService {
    private final StringBuilder dataSchemas;

    public DataBaseService() {
        this.dataSchemas = new StringBuilder();
    }

    public static ArrayList<String> filterSchemas(ConnectionFactory connectionFactory) {
        ArrayList<String> listSchema = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSets = metaData.getCatalogs();
            while (resultSets.next()) {
                String schemaName = resultSets.getString(1);
                if (!schemaName.equalsIgnoreCase("information_schema") &&
                        !schemaName.equalsIgnoreCase("mysql") &&
                        !schemaName.equalsIgnoreCase("performance_schema") &&
                        !schemaName.equalsIgnoreCase("sys")) {
                    listSchema.add(schemaName);
                }
            }
            resultSets.close();
        } catch (SQLException e) {
            System.out.println("Error FilterSchemas: " + e.getMessage());
        }
        return listSchema;
    }

    public void dataSchema(String nameSchema, ConnectionFactory connectionFactory) {
        ClearSchemas();
        try (Connection conn = DriverManager.getConnection(connectionFactory.getURL() + "/" + nameSchema, connectionFactory.getUSER(), connectionFactory.getPASSWORD())) {
            String query = "SELECT * FROM information_schema.tables WHERE table_schema = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nameSchema);
                ResultSet tables = preparedStatement.executeQuery();

                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    dataSchemas.append("CREATE TABLE ").append(tableName).append(" (\n");

                    ResultSet columns = conn.getMetaData().getColumns(null, nameSchema, tableName, null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String dataType = columns.getString("TYPE_NAME");
                        dataSchemas.append("    ").append(columnName).append(" ").append(dataType).append(",\n");
                    }
                    columns.close();
                    dataSchemas.append(");\n\n");
                }
                tables.close();
            }
        } catch (SQLException e) {
            System.out.println("Error generating DDL: " + e.getMessage());
        }
    }

    public void ClearSchemas() {
        this.dataSchemas.delete(0, this.dataSchemas.length());
    }
    public StringBuilder getDataSchemas() {
        return this.dataSchemas;
    }
}
