package meuapp.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import meuapp.config.ConnectionFactory;

public class DataBaseService {
    private ArrayList<String> schemas;
    private StringBuilder dataSchemas;
    private ConnectionFactory connectionFactory;

    public DataBaseService() {
        this.schemas = new ArrayList<>();
        this.dataSchemas = new StringBuilder();
        this.connectionFactory = new ConnectionFactory();
    }

    public void FilterSchemas() {
        try (Connection connection = connectionFactory.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSets = metaData.getCatalogs();
            while (resultSets.next()) {
                String schemaName = resultSets.getString(1);
                if (!schemaName.equalsIgnoreCase("information_schema") &&
                        !schemaName.equalsIgnoreCase("mysql") &&
                        !schemaName.equalsIgnoreCase("performance_schema") &&
                        !schemaName.equalsIgnoreCase("sys")) {
                    this.schemas.add(schemaName);
                }
            }
            resultSets.close();
        } catch (SQLException e) {
            System.out.println("Error FilterSchemas: " + e.getMessage());
        }
    }

    public void DataSchema(String nameSchema) {
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
                    System.out.println(dataSchemas);
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
    public ArrayList<String> getSchemas() {
        return this.schemas;
    }

    public StringBuilder getDataSchemas() {
        return this.dataSchemas;
    }

}
