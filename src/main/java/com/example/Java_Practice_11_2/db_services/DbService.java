package com.example.Java_Practice_11_2.db_services;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DbService {


    public boolean dropTapbe() {
        String command = "DROP TABLE notepads";

        try {
            return executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean createTable() throws SQLException {

        String createTableQuery = "CREATE TABLE cars (" +
                "id SERIAL PRIMARY KEY," +
                "mark VARCHAR(50)," +
                "model VARCHAR(50)," +
                "engine_volume NUMERIC(5,2)," +
                "year INT," +
                "color VARCHAR(50)," +
                "body_type VARCHAR(50))";

        return executeAction(createTableQuery);
    }

    public void fillTable() throws SQLException {

        String sqlCommand = "INSERT INTO cars (mark, model, engine_volume, " +
                "year, color, body_type)\n" +
                "VALUES\n" +
                "('Nissan', 'GTR-R34', 2.8, '1986', 'silver', 'sedan'),\n" +
                "('Nissan', 'Silvia', 2.6, '1988', 'gray', 'sedan'),\n" +
                "('Nissan', 'Nismo', 3.8, '1988', 'black', 'sedan'),\n" +
                "('VW', 'Caddy', 1.9, '2015', 'white', 'universal'),\n" +
                "('Skoda', 'Octavia', 2.1, '2019', 'gray-metalic', 'universal'),\n" +
                "('Ford', 'Focus', 1.6, '2007', 'black', 'universal'),\n" +
                "('Renault', 'Megan', 1.5, '2007', 'gray', 'universal'),\n" +
                "('VW', 'Golf', 1.6, '2000', 'black', 'hatchback'),\n" +
                "('Mercedes', 'A-Class', 1.4, '2000', 'blue', 'hatchback')";


        executeAction(sqlCommand);
    }


    private boolean executeAction(String command) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        boolean isSuccess = false;
        try {
            int rows = statement.executeUpdate(command);

            if (rows > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        statement.close();
        connection.close();

        return isSuccess;
    }

    private ResultSet selectAction(String command) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        try {
            return statement.executeQuery(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private ArrayList<String> createParams(ResultSet content) throws SQLException {
        ArrayList<String> params = new ArrayList<String>();
        ResultSetMetaData metaData = content.getMetaData();
        while (content.next()) {
            params.add(content.getString(1));
        }

        return params;
    }

    public ArrayList<String> getParams(String table, String paramName) throws SQLException {
        String command = String.format("SELECT DISTINCT %s" +
                " FROM %s ORDER BY %s", paramName, table, paramName);

        return createParams(Objects.requireNonNull(selectAction(command)));
    }

    public Integer getMinIntParam(String table, String paramName) throws SQLException {
        String command = String.format("SELECT  %s" +
                " FROM %s ORDER BY %s" +
                " LIMIT 1", paramName, table, paramName);

        ResultSet result = selectAction(command);
        return Objects.requireNonNull(result).getInt(1);
    }

    public Integer getMaxIntParam(String table, String paramName) throws SQLException {
        String command = String.format("SELECT  %s" +
                " FROM %s ORDER BY %s DESC" +
                " LIMIT 1", paramName, table, paramName);

        ResultSet result = selectAction(command);
        return Objects.requireNonNull(result).getInt(1);
    }


    public ResultSet selectAllFromTable(String table) throws SQLException {
        String command = String.format("SELECT * FROM %s" +
                " ORDER BY id", table);

        return selectAction(command);
    }

    public ResultSet selectAllDistinct(String table, String param) throws SQLException {
        String command = String.format("SELECT DISTINCT %s FROM %s", param, table);

        return selectAction(command);
    }

    public ResultSet selectCountByParam(String table, String param) throws SQLException {
        String command = String.format("SELECT %s, COUNT(id)" +
                " FROM %s" +
                " GROUP BY %s", param, table, param);

        return selectAction(command);
    }

    public ResultSet selectNotepadsCountByManufacter() throws SQLException {
        String command = "SELECT manufacturer_name, COUNT(notebook_id)" +
                " FROM notepads" +
                " GROUP BY manufacturer_name";

        return selectAction(command);
    }

    public ResultSet selectMaxCountByParam(String table, String param) throws SQLException {
        String command = String.format(
                "SELECT %s, COUNT(id) AS count_%s" +
                        " FROM %s" +
                        " GROUP BY %s" +
                        " ORDER BY count_%s DESC" +
                        " LIMIT 1",
                param, table, table, param, table
        );

        return selectAction(command);
    }

    public ResultSet selectMinCountByParam(String table, String param) throws SQLException {
        String command = String.format(
                "SELECT %s, COUNT(id) AS count_%s" +
                        " FROM %s" +
                        " GROUP BY %s" +
                        " ORDER BY count_%s" +
                        " LIMIT 1",
                param, table, table, param, table
        );

        return selectAction(command);
    }

    public ResultSet selectMaxNotepadsByManufacture() throws SQLException {
        String command = "SELECT manufacturer_name, COUNT(notebook_id) AS count_notepads" +
                " FROM notepads" +
                " GROUP BY manufacturer_name" +
                " ORDER BY count_notepads DESC" +
                " LIMIT 1";

        return selectAction(command);
    }

    public ResultSet selectMinNotepadsByManufacture() throws SQLException {
        String command = "SELECT manufacturer_name, COUNT(notebook_id) AS count_notepads" +
                " FROM notepads" +
                " GROUP BY manufacturer_name" +
                " ORDER BY count_notepads" +
                " LIMIT 1";

        return selectAction(command);
    }

    public ResultSet selectSoftNotepads() throws SQLException {
        String command = "SELECT * FROM notepads" +
                " WHERE cover_type='м`яка'";

        return selectAction(command);
    }

    public ResultSet selectHardNotepads() throws SQLException {
        String command = "SELECT * FROM notepads" +
                " WHERE cover_type='тверда'";

        return selectAction(command);
    }

    public ResultSet selectAllByStringParam(String table, String paramName, String param) throws SQLException {
        String command = String.format("SELECT * FROM %s" +
                " WHERE %s='%s'",table, paramName, param);

        return selectAction(command);
    }

    public ResultSet selectAllByIntParam(String table, String paramName, Integer param) throws SQLException {
        String command = String.format("SELECT * FROM %s" +
                " WHERE %s=%d",table, paramName, param);

        return selectAction(command);
    }

    public ResultSet selectAllByIntDiapason(
            String table,
            String paramName,
            int begin,
            int end
    ) throws SQLException {
        String orderType = "";
        if(begin > end) {
            int z = begin;
            begin = end;
            end = z;
            orderType = "DESC";
        }
        String command = String.format("SELECT * FROM %s" +
                " WHERE %s BETWEEN %d AND %d" +
                " ORDER BY %s %s",
                table, paramName, begin, end, paramName, orderType);

        System.out.println(command);

        return selectAction(command);
    }

    public ResultSet selectNotepadsByLayout(String layout) throws SQLException {
        String command = String.format("SELECT * FROM notepads" +
                " WHERE page_layout='%s'", layout);

        return selectAction(command);
    }

    public ArrayList<String> getNotepadCountries() throws SQLException {

        return getParams("notepads", "manufacturer_country");
    }

    public ArrayList<String> getNotepadLayouts() throws SQLException {

        return getParams("notepads", "page_layout");
    }

    public ResultSet selectAllOrderByPages(String orderBy) throws SQLException {
        String isDesc = orderBy.equals("up") ? "" : "DESC";
        String command = String.format("SELECT * FROM notepads" +
                " ORDER BY number_of_pages %s", isDesc);

        return selectAction(command);
    }

    public void addRow(Map<String, String> params) throws SQLException {
        String sqlCommand = String.format("INSERT INTO cars" +
                        " (mark, model, engine_volume, " +
                        "year, color, body_type)\n" +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                params.get("mark"),
                params.get("model"),
                Float.parseFloat(params.get("engine_volume")),
                params.get("year"),
                params.get("color"),
                params.get("body_type")
        );

        executeAction(sqlCommand);
    }

    public ArrayList<String> getIds() throws SQLException {
        return getParams("notepads", "notebook_id");
    }

    public ArrayList<String> getCoverTypes() throws SQLException {
        return getParams("notepads", "cover_type");
    }

    public ArrayList<String> getPageLayouts() throws SQLException {
        return getParams("notepads", "page_layout");
    }

    public void deleteRowById(String table, int rowId) throws SQLException {
        String command = String.format("DELETE FROM %s" +
                " WHERE id=%d",table, rowId);

        executeAction(command);
    }

    public Map<String, String> getRowById(String table, String rowId) throws SQLException {
        if (rowId == null) {
            return null;
        }
        String command = String.format("SELECT * FROM %s" +
                " WHERE id=%d",table, Integer.parseInt(rowId));

        return getMapWithContent(Objects.requireNonNull(selectAction(command)));
    }

    private Map<String, String> getMapWithContent(ResultSet content) {
        try {
            Map<String, String> params = new HashMap<>();
            ResultSetMetaData metaData = content.getMetaData();
            while (content.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String key = metaData.getColumnName(i);
                    String value = content.getString(i);
                    params.put(key, value);
                }
            }

            content.close();
            return params;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Map<String, ArrayList<String>> getOptions(String table, String[] attributes) throws SQLException {
        Map<String, ArrayList<String>> options = new HashMap<>();

        for (String attribute : attributes) {
            options.put(attribute, getParams(table, attribute));
        }

        return options;
    }

    public void updateRow(HttpServletRequest request) {
        String sqlCommand = "UPDATE cars " +
                " SET" +
                " mark = ?," +
                " model = ?," +
                " engine_volume = ?," +
                " year = ?," +
                " color = ?," +
                " body_type = ?" +
                " WHERE id = ?";

        executeUpdate(sqlCommand, request);
    }

    private void executeUpdate(String sqlCommand, HttpServletRequest request) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement prepared = connection.prepareStatement(sqlCommand);

            prepared.setString(1, request.getParameter("mark"));
            prepared.setString(2, request.getParameter("model"));
            prepared.setObject(3, Float.parseFloat(request.getParameter("engine_volume")));
            prepared.setInt(4, Integer.parseInt( request.getParameter("year")));
            prepared.setString(5, request.getParameter("color"));
            prepared.setObject(6, request.getParameter("body_type"));
            prepared.setInt(7, Integer.parseInt(request.getParameter("row_id")));

            int rows = prepared.executeUpdate();

            prepared.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
