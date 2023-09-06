package com.example.Java_Practice_11_2.repositories;

import com.example.Java_Practice_11_2.Models.Customer;
import com.example.Java_Practice_11_2.db_services.DatabaseConnection;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepository extends BaseRepository {

    public static void createTable() {
        String command = "CREATE TABLE IF NOT EXISTS customers (" +
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

    public static void fillTable() {
        String sqlCommand = "INSERT INTO customers (name, phone, email)" +
                " VALUES\n" +
                "('Покупатель 1', '+1111111', 'mail1@com.ua')," +
                "('Покупатель 2', '+2222222', 'mail2@com.ua')," +
                "('Покупатель 3', '+3333333', 'mail3@com.ua')," +
                "('Покупатель 4', '+4444444', 'mail4@com.ua')," +
                "('Покупатель 5', '+5555555', 'mail5@com.ua')," +
                "('Покупатель 6', '+6666666', 'mail6@com.ua')";
        try {
            executeAction(sqlCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Customer> getAllCustomers() {
        String command = "SELECT * FROM customers ORDER BY id";
        try {
            ResultSet result = selectAction(command);
            return createList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static ArrayList<Customer> createList(ResultSet content) throws SQLException {
        if (content == null) {
            return null;
        }

        ArrayList<Customer> list = new ArrayList<>();
        try {
            while (content.next()) {
                Customer customer = new Customer();
                customer.setId(content.getInt("id"));
                customer.setName(content.getString("name"));
                customer.setPhone(content.getString("phone"));
                customer.setEmail(content.getString("email"));
                list.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        content.close();
        return list;
    }

    public static Object getCustomersOptions(String columnName) {
        return getOptions(columnName);
    }

    private static ArrayList<Object> getOptions(String columnName) {
        String command = String.format("SELECT DISTINCT %s FROM customers" +
                " ORDER BY %s", columnName, columnName);

        try {
            return getOptionList(selectAction(command), columnName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void addCustomer(HttpServletRequest request) {
        String command = String.format("INSERT INTO customers" +
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

    public static Object getCustomerById(int id) {
        String command = String.format("SELECT * FROM customers" +
                " WHERE id=%d", id);

        try {
            return createCustomer(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static Customer createCustomer(ResultSet result) {
        if (result == null) {
            return null;
        }
        try {
            if (result.next()) {
                Customer customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setName(result.getString("name"));
                customer.setEmail(result.getString("email"));
                customer.setPhone(result.getString("phone"));

                return customer;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public static void updateCustomer(HttpServletRequest request) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String command = "UPDATE customers" +
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

    public static void deleteCustomerById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        String command = String.format("DELETE FROM product_sales" +
                " WHERE customer_id = %d", id);

        try {
            executeAction(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        command = String.format("DELETE FROM customers" +
                " WHERE id = %d", id);

        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
