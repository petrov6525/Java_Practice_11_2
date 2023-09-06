package com.example.Java_Practice_11_2.repositories;


import com.example.Java_Practice_11_2.db_services.DatabaseConnection;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BaseRepository {
    protected static void executeAction (String command) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            statement.executeUpdate(command);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static  ResultSet selectAction(String command) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        try {
            return statement.executeQuery(command);
        } catch (SQLException e) {
            System.out.println("Error while select: "+e.getMessage());
            System.out.println(command);
            return null;
        }
    }

    protected static ArrayList<Object> getOptionList(ResultSet content, String columnName) {
        if (content == null) {
            return null;
        }
        ArrayList<Object> list = new ArrayList<>();
        while(true) {
            try {
                if (!content.next()) break;
                list.add(content.getObject(columnName));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }

        }
        return list;
    }

    public static void createTables ()
    {
        SalesMenRepository.createTable();
        CustomerRepository.createTable();
        ProductSalesRepository.createTable();
    }

    public static void fillTables ()
    {
//        SalesMenRepository.fillTable();
//        CustomerRepository.fillTable();
        ProductSalesRepository.fillTable();
    }
}
