package com.example.Java_Practice_11_2.repositories;

import com.example.Java_Practice_11_2.Models.Customer;
import com.example.Java_Practice_11_2.Models.Product;
import com.example.Java_Practice_11_2.Models.Sale;
import com.example.Java_Practice_11_2.Models.SalesMan;
import com.example.Java_Practice_11_2.db_services.DatabaseConnection;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Double.parseDouble;

public class ProductSalesRepository extends BaseRepository {
    public static void createTable() {
        String command = "CREATE TABLE IF NOT EXISTS product_sales (" +
                "id SERIAL PRIMARY KEY," +
                "product_name VARCHAR(50)," +
                "sale_price NUMERIC(10, 2)," +
                "sale_date TIMESTAMP," +
                "customer_id INTEGER REFERENCES customers(id)," +
                "sales_man_id INTEGER REFERENCES sales_men(id));";

        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void fillTable() {
        String sqlCommand = "INSERT INTO product_sales (product_name, sale_price, sale_date," +
                "customer_id, sales_man_id)\n" +
                " VALUES\n" +
                "('Продукт 1', 1.99, NOW(), 1,1)," +
                "('Продукт 2', 2.99, NOW(), 2,2)," +
                "('Продукт 3', 3.99, NOW(), 3,3)," +
                /*"('Продукт 4', 4.99, NOW(), 4,4)," +
                "('Продукт 5', 5.99, NOW(), 5,5)," +
                "('Продукт 6', 6.99, NOW(), 6,6)," +
                "('Продукт 7', 7.99, NOW(), 5,2)," +
                "('Продукт 8', 8.99, NOW(), 5,3)," +
                "('Продукт 9', 9.99, NOW(), 5,4)," +
                "('Продукт 10', 10.99, NOW(), 4,6)," +
                "('Продукт 11', 11.99, NOW(), 2,3)," +
                "('Продукт 12', 12.99, NOW(), 2,4)," +
                "('Продукт 13', 13.99, NOW(), 2,5)," +
                "('Продукт 14', 14.99, NOW(), 2,6)," +
                "('Продукт 15', 15.99, NOW(), 3,1)," +
                "('Продукт 16', 16.99, NOW(), 3,2)," +
                "('Продукт 17', 17.99, NOW(), 3,4)," +
                "('Продукт 18', 18.99, NOW(), 3,5)," +
                "('Продукт 19', 19.99, NOW(), 3,6)," +
                "('Продукт 20', 20.99, NOW(), 4,1)," +
                "('Продукт 21', 21.99, NOW(), 4,2)," +
                "('Продукт 22', 22.99, NOW(), 4,3)," +
                "('Продукт 23', 23.99, NOW(), 4,5)," +
                "('Продукт 24', 24.99, NOW(), 4,6)," +
                "('Продукт 25', 25.99, NOW(), 5,1)," +
                "('Продукт 26', 26.99, NOW(), 5,2)," +
                "('Продукт 27', 27.99, NOW(), 5,3)," +
                "('Продукт 28', 28.99, NOW(), 5,4)," +*/
                "('Продукт 29', 29.99, NOW(), 5,6)";

        try {
            executeAction(sqlCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Product> getAllProducts() {
        String command = "SELECT product_name, sale_price FROM product_sales";

        try {
            ResultSet result = selectAction(command);
            return getProductsList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static ArrayList<Product> getProductsList(ResultSet content) {
        if (content == null) {
            return null;
        }
        ArrayList<Product> list = new ArrayList<>();

        while (true) {
            try {
                if (!content.next()) break;
                Product product = new Product();
                product.setName(content.getString("product_name"));
                product.setPrice(content.getDouble("sale_price"));
                list.add(product);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return list;
    }

    public static ArrayList<Sale> getAllSales() {
        String command = "SELECT ps.id AS sale_id, ps.sale_date, ps.sale_price, ps.product_name," +
                " c.id AS customer_id, c.name AS customer_name," +
                " sm.id AS sales_man_id, sm.name AS sales_man_name" +
                " FROM product_sales AS ps" +
                " JOIN customers AS c ON ps.customer_id = c.id" +
                " JOIN sales_men AS sm ON ps.sales_man_id = sm.id";
        try {
            ResultSet result = selectAction(command);
            return getSalesList(result);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected static ArrayList<Sale> getSalesList(ResultSet content) {
        if (content == null) {
            return null;
        }

        ArrayList<Sale> sales = new ArrayList<>();

        while (true) {
            try {
                if (!content.next()) break;
                Sale sale = new Sale();
                sale.setId(content.getInt("sale_id"));
                sale.setDate(content.getDate("sale_date"));

                Product product = new Product();
                product.setName(content.getString("product_name"));
                product.setPrice(content.getDouble("sale_price"));
                sale.setProduct(product);

                SalesMan salesMan = new SalesMan();
                salesMan.setId(content.getInt("sales_man_id"));
                salesMan.setName(content.getString("sales_man_name"));
                sale.setSalesMan(salesMan);

                Customer customer = new Customer();
                customer.setId(content.getInt("customer_id"));
                customer.setName(content.getString("customer_name"));
                sale.setCustomer(customer);

                sales.add(sale);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }

        }
        return sales;
    }

    public static ArrayList<Object> getDates() {
        return getOptions("sale_date");
    }


    public static ArrayList<Object> getOptions(String columnName) {
        String command = String.format("SELECT DISTINCT %s FROM product_sales" +
                " ORDER BY %s", columnName, columnName);

        try {
            return getOptionList(selectAction(command), columnName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Sale> getSalesByDate(String dateText)  {
        if (dateText == null) {
            return null;
        }
        String command = String.format("SELECT ps.id AS sale_id, ps.sale_date, ps.sale_price, ps.product_name," +
                " c.id AS customer_id, c.name AS customer_name," +
                " sm.id AS sales_man_id, sm.name AS sales_man_name" +
                " FROM product_sales AS ps" +
                " JOIN customers AS c ON ps.customer_id = c.id" +
                " JOIN sales_men AS sm ON ps.sales_man_id = sm.id" +
                " WHERE sale_date = '%s'" +
                " ORDER BY sale_id", dateText);
        try {
            return getSalesList(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Object getSalesByDateDiapason(String dateBegin, String dateEnd) {
        if (dateBegin == null || dateEnd == null) {
            return null;
        }
        String command = String.format("SELECT ps.id AS sale_id, ps.sale_date, ps.sale_price, ps.product_name," +
                " c.id AS customer_id, c.name AS customer_name," +
                " sm.id AS sales_man_id, sm.name AS sales_man_name" +
                " FROM product_sales AS ps" +
                " JOIN customers AS c ON ps.customer_id = c.id" +
                " JOIN sales_men AS sm ON ps.sales_man_id = sm.id" +
                " WHERE sale_date BETWEEN '%s' and '%s'" +
                " ORDER BY sale_id", dateBegin, dateEnd);
        try {
            return getSalesList(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Object getSalesBySalesMan(String salesMan) {
        if (salesMan == null) {
            return null;
        }
        String command = String.format("SELECT ps.id AS sale_id, ps.sale_date, ps.sale_price, ps.product_name," +
                " c.id AS customer_id, c.name AS customer_name," +
                " sm.id AS sales_man_id, sm.name AS sales_man_name" +
                " FROM product_sales AS ps" +
                " JOIN customers AS c ON ps.customer_id = c.id" +
                " JOIN sales_men AS sm ON ps.sales_man_id = sm.id" +
                " WHERE sm.name = '%s'" +
                " ORDER BY sale_id", salesMan);
        try {
            return getSalesList(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Object getSalesByCustomer(String customer) {
        if (customer == null) {
            return null;
        }
        String command = String.format("SELECT ps.id AS sale_id, ps.sale_date, ps.sale_price, ps.product_name," +
                " c.id AS customer_id, c.name AS customer_name," +
                " sm.id AS sales_man_id, sm.name AS sales_man_name" +
                " FROM product_sales AS ps" +
                " JOIN customers AS c ON ps.customer_id = c.id" +
                " JOIN sales_men AS sm ON ps.sales_man_id = sm.id" +
                " WHERE c.name = '%s'" +
                " ORDER BY sale_id", customer);
        try {
            return getSalesList(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> getTheBestSalesMan() {
        String command = "SELECT sm.name AS sales_man_name, SUM(ps.sale_price) as total_sales" +
                " FROM product_sales ps" +
                " JOIN sales_men sm ON ps.sales_man_id = sm.id" +
                " GROUP BY sales_man_name" +
                " ORDER BY total_sales DESC" +
                " LIMIT 1";
        try {
            ResultSet result = selectAction(command);
            Map<String, Object> map = new HashMap<>();
            if (result == null) {
                return null;
            }
            result.next();
            map.put("salesMan", result.getObject("sales_man_name"));
            map.put("maxSum", result.getObject("total_sales"));

            return map;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> getTheBestCustomer() {
        String command = "SELECT c.name AS customer_name, SUM(ps.sale_price) as total_sales" +
                " FROM product_sales ps" +
                " JOIN customers c ON ps.customer_id = c.id" +
                " GROUP BY customer_name" +
                " ORDER BY total_sales DESC" +
                " LIMIT 1";
        try {
            ResultSet result = selectAction(command);
            Map<String, Object> map = new HashMap<>();
            if (result == null) {
                return null;
            }
            result.next();
            map.put("customer", result.getObject("customer_name"));
            map.put("maxSum", result.getObject("total_sales"));

            return map;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Object getAvgSalePrice() {
        String command = "SELECT AVG(ps.sale_price) AS avg" +
                " FROM product_sales ps";

        try {
            ResultSet result = selectAction(command);
            if (result == null) {
                return null;
            }
            result.next();
            return result.getObject("avg");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Product getMostPopularProduct() {
        String command = "SELECT product_name, sale_price" +
                " FROM product_sales" +
                " GROUP BY product_name, sale_price" +
                " ORDER BY COUNT(*) DESC" +
                " LIMIT 1";

        try {
            return getProductFromResult(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Product getProductFromResult(ResultSet result) {
        try {
            result.next();
            Product product = new Product();
            product.setPrice(result.getDouble("sale_price"));
            product.setName(result.getString("product_name"));

            return product;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    protected static void executeActionWithPrepared(String command, HttpServletRequest request) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, request.getParameter("product_name"));
            preparedStatement.setDouble(2, Double.parseDouble(request.getParameter("sale_price")));
            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("customer_id")));
            preparedStatement.setInt(4, Integer.parseInt(request.getParameter("sales_man_id")));

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addProductSale(HttpServletRequest request) {
        try {
            String command = "INSERT INTO product_sales " +
                    "(product_name, sale_price, sale_date, customer_id, sales_man_id)" +
                    " VALUES (?, ?, NOW(), ?, ?)";

            executeActionWithPrepared(command, request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object getProductSalesOptions(String columnName) {
        return getOptions(columnName);
    }

    public static Sale getSaleById(int id) {
        String command = String.format("SELECT * FROM product_sales" +
                " WHERE id = %d", id);

        try {
            return createSale(selectAction(command));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Sale createSale(ResultSet result) {
        if (result == null) {
            return null;
        }
        try {
            if (result.next()) {

                Sale sale = new Sale();
                sale.setId(result.getInt("id"));

                Product product = new Product();
                product.setName(result.getString("product_name"));
                product.setPrice(result.getDouble("sale_price"));
                sale.setProduct(product);

                Customer customer = new Customer();
                customer.setId(result.getInt("customer_id"));
                sale.setCustomer(customer);

                SalesMan man = new SalesMan();
                man.setId(result.getInt("sales_man_id"));
                sale.setSalesMan(man);

                return sale;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

    public static void updateSale(HttpServletRequest request) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            String command = "UPDATE product_sales" +
                    " SET" +
                    " product_name = ?, sale_price = ?, sale_date = NOW()," +
                    " customer_id = ?, sales_man_id = ?" +
                    " WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setString(1, request.getParameter("product_name"));
            preparedStatement.setDouble(2, parseDouble(request.getParameter("sale_price")));
            preparedStatement.setInt(3, Integer.parseInt(request.getParameter("customer_id")));
            preparedStatement.setInt(4, Integer.parseInt(request.getParameter("sales_man_id")));
            preparedStatement.setInt(5, Integer.parseInt( request.getParameter("id")));

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteProductSaleById(HttpServletRequest request) {
        String command = String.format("DELETE FROM product_sales" +
                " WHERE id = %d", Integer.parseInt(request.getParameter("id")));

        try {
            executeAction(command);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
