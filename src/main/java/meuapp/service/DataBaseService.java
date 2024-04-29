package meuapp.service;


import meuapp.config.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;


public class    DataBaseService extends ConnectionFactory{
    ArrayList<String> schemas;
    StringBuilder dataSchemas;

    public DataBaseService(){
        this.schemas = new ArrayList<>();
        this.dataSchemas = new StringBuilder();
    }

    public void FilterSchemas(){
        try (Connection connection = ConnectionFactory.getConnection()) {
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
        this.dataSchemas.append("[SCHEMA NAME: ").append(nameSchema).append("] ");
        try (Connection conn = DriverManager.getConnection(URL + "/" + nameSchema, USER, PASSWORD)) {
            String query = "SELECT * FROM information_schema.tables WHERE table_schema = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nameSchema);
                ResultSet tables = preparedStatement.executeQuery();

                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    this.dataSchemas.append("[TABLE NAME: ").append(tableName).append("] ");
                    ResultSet columns = conn.getMetaData().getColumns(null, nameSchema, tableName, null);

                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        this.dataSchemas.append("[COLUMN NAME: ").append(columnName).append("] ");
                    }
                    columns.close();
                }
                tables.close();
            }
        } catch (SQLException e) {
            System.out.println("Error DataSchema: " + e.getMessage());
        }
    }

    public ArrayList<String> getSchemas() {
        return this.schemas;
    }

    public StringBuilder getDataSchemas() {
        return this.dataSchemas;
    }


}


