package meuapp.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBResultFormatter {
    public String FormatResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        String columns = "";
        String rows = "";
        for (int i = 1; i <= metadata.getColumnCount(); i++) {
            columns += "<th>" + metadata.getColumnName(i) + "</th>";
        }
        while (resultSet.next()) {
            rows += "<tr>";
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                rows += "<td>" + resultSet.getString(i) + "</td>";
            }
            rows += "</tr>";
        }
        return "<table>" + columns + rows + "</table>";
    }
}
