package meuapp.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBResultFormatter {
    public String FormatResult(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metadata = resultSet.getMetaData();
        int colcount = metadata.getColumnCount();
        String columns = "";
        String rows = "";
        if (resultSet.isLast()) {
            return "Nenhum resultado encontrado.";
        }
        if (colcount > 1) {
            for (int i = 1; i <= colcount; i++) {
                columns += "<th>" + metadata.getColumnName(i) + "</th>";
            }
            while (resultSet.next()) {
                rows += "<tr>";
                for (int i = 1; i <= colcount; i++) {
                    rows += "<td>" + resultSet.getString(i) + "</td>";
                }
                rows += "</tr>";
            }
            return "<table>" + columns + rows + "</table>";
        } else {
            while (resultSet.next()) {
                rows += resultSet.getString(1) + (resultSet.isLast() ? "." : ", ");
            }
            return rows;
        }
    }
}
