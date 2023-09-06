package com.example.Java_Practice_11_2.repositories;

import com.example.Java_Practice_11_2.Models.SalesMan;
import com.example.Java_Practice_11_2.db_services.DatabaseConnection;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalesMenRepository extends BaseRepository{
    public static void createTable() {
        String command = "CREATE TABLE IF NOT EXISTS sales_men (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(50)," +
                "phone VARCHAR(50)," +
                "email VARCHAR(50));";

        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void fillTable () {
        String sqlCommand = "INSERT INTO sales_men (name, phone, email)\n" +
                " VALUES\n" +
                "('Продавец 1', '+1111111', 'mail1@com.ua')," +
                "('Продавец 2', '+2222222', 'mail2@com.ua')," +
                "('Продавец 3', '+3333333', 'mail3@com.ua')," +
                "('Продавец 4', '+4444444', 'mail4@com.ua')," +
                "('Продавец 5', '+5555555', 'mail5@com.ua')," +
                "('Продавец 6', '+6666666', 'mail6@com.ua')";
        try {
            executeAction(sqlCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SalesMan> getAllSalesMen () {
        String command = "SELECT * FROM sales_men ORDER BY id";
        try {
            ResultSet result = selectAction(command);
            return createList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static ArrayList<SalesMan> createList(ResultSet content) throws SQLException {
        if (content == null) {
            return null;
        }

        ArrayList<SalesMan> list = new ArrayList<>();
        try {
            while (content.next()) {
                SalesMan salesMan = new SalesMan();
                salesMan.setId(content.getInt("id"));
                salesMan.setName(content.getString("name"));
                salesMan.setPhone(content.getString("phone"));
                salesMan.setEmail(content.getString("email"));
                list.add(salesMan);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        content.close();
        return list;
    }

    public static ArrayList<Object> getSalesMenOptions(String columnName) {
        return getOptions(columnName);
    }

    private static ArrayList<Object> getOptions(String columnName) {
        String command = String.format("SELECT DISTINCT %s FROM sales_men" +
                " ORDER BY %s", columnName, columnName);

        try {
            return getOptionList(selectAction(command), columnName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void addSalesMan(HttpServletRequest request) {
        String command = String.format("INSERT INTO sales_men" +
                        "(name, email, phone) VALUES" +
                        "('%s', '%s', '%s')", request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("phone"));
        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static SalesMan getSalesManById(int id) {
        String command = String.format("SELECT * FROM sales_men" +
                " WHERE id = %d", id);

        try {
            return createSalesMan(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static SalesMan createSalesMan(ResultSet result) {
        if (result == null) {
            return null;
        }
        try {
            if (result.next()) {
                SalesMan man = new SalesMan();
                man.setId(result.getInt("id"));
                man.setName(result.getString("name"));
                man.setEmail(result.getString("email"));
                man.setPhone(result.getString("phone"));

                return man;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public static void updateSalesMan(HttpServletRequest request) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String command = "UPDATE sales_men" +
                    " SET" +
                    " name = ?, phone = ?, email = ?" +
                    " WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, request.getParameter("name"));
            preparedStatement.setString(2, request.getParameter("phone"));
            preparedStatement.setString(3, request.getParameter("email"));
            preparedStatement.setInt(4, Integer.parseInt( request.getParameter("id")));

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteSalesManById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        String command = String.format("DELETE FROM product_sales" +
                " WHERE sales_man_id = %d", id);

        try {
            executeAction(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        command = String.format("DELETE FROM sales_men" +
                " WHERE id = %d", Integer.parseInt(request.getParameter("id")));

        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
