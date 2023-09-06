package com.example.Java_Practice_11_2.Helpers;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class HtmlWriter {
    HttpServletResponse response;
    PrintWriter out;

    public void init(HttpServletResponse _response) throws IOException {
        response = _response;
        response.setContentType("text/html; charset=UTF-8");
        out = response.getWriter();
    }

    public void writeRootHref() {
        out.println("<div>");
        out.println("<a href='/' tittle='Home'>");
        out.println("Home");
        out.println("</a>");
        out.println("</div>");

    }

    private void writeCSS(){
        out.println("<style>");
        out.println("html {\n" +
                "      background-color: bisque;\n" +
                "    }\n" +
                "    a {\n" +
                "      display: flex;\n" +
                "      align-items: center;\n" +
                "      border-radius: 8px;\n" +
                "      transition: 0.3s;\n" +
                "      text-decoration: none;\n" +
                "      color: darkgoldenrod;\n" +
                "      width: 200px;\n" +
                "      height: 50px;\n" +
                "      padding-left: 10px;\n" +
                "    }\n" +
                "    div {\n" +
                "      width: fit-content;\n" +
                "    }\n" +
                "    a:hover {\n" +
                "      background: beige;\n" +
                "    }");
        out.println("</style>");
    }

    public void beginDocument() {

        out.println("<html>");
        out.println("<head>");
        writeCSS();
        out.println("</head>");
        out.println("<body>");
        writeRootHref();
    }

    public void endDocument() {
        out.println("</body></html>");
    }

    public void writeH1(String content) {
        out.println("<h1>" + content + "</h1>");
    }

    public  void writeContent(Object object) {
        out.println(object);
    }

    public void writeTable(ResultSet content) throws SQLException {
        ResultSetMetaData metaData = content.getMetaData();
        out.println("<table border='1'>");
        out.println("<tr>");
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            out.println("<th>");
            out.println(columnName);
            out.println("</th>");
        }
        out.println("</tr>");

        while (content.next()) {
            out.println("<tr>");
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String value = content.getString(i);
                out.println("<td>");
                out.println(value);
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</table>");
        content.close();
    }

    public void writeCountryForm(ArrayList<String> countries) {
        beginDocument();
        writeH1("Notepads By Country");
        out.println("<form action='' method='get'>");
        printSelectInput(countries, "country", "");
        out.println("<button type='submit'>Select</button>");

        endDocument();
    }

    private void printSelectInput (ArrayList<String> options, String name, String value) {
        out.println(String.format("<select name='%s' >", name));
        for (String option : options) {
            if (option.equals(value)) {
                out.println(String.format("<option value='%s' selected >%s</option>", option, option));
            }
            else {
                out.println(String.format("<option value='%s'>%s</option>", option, option));
            }
        }
        out.println("</select>");
    }

    public void writeLayoutForm(ArrayList<String> layouts) {
        beginDocument();
        writeH1("Notepads By Layout");
        out.println("<form action='' method='get'>");
        printSelectInput(layouts, "layout", "");
        out.println("<button type='submit'>Select</button>");

        endDocument();
    }

    public void writeOrderByPagesForm(String orderBy) {
        beginDocument();
        writeH1("Notepads By Pages");
        String checked = orderBy.equals("up") ? "checked" : "";
        out.println("<form action='' method='get'>");
        out.println(String.format("<label>Up <input type='radio' " +
                "name='orderBy' value='up' %s /></label>",checked));

        checked = orderBy.equals("down") ? "checked" : "";
        out.println(String.format("<label>Down <input type='radio'" +
                " name='orderBy' value='down' %s /></label>",checked));
        out.println("<button type='submit'>Select</button>");

        endDocument();
    }

    private void printTextInput(String name, String value) {
        out.println(String.format("<input type='text' name='%s' value='%s' required />", name, value));
    }

    private void printNumberInput(String name, String value) {
        out.println(String.format(
                "<input type='number' min='1' name='%s' value='%d' required />",
                name, Integer.parseInt(value))
        );
    }

    private void printTextInputWithLabel(String label, String inputName, String value) {
        out.println("<label style='display: inline-block'>");
        out.println(String.format("<p>%s</p>", label));
        printTextInput(inputName, value);
        out.println("</label>");
    }

    private void printNumberInputWithLabel(String label, String inputName, String value) {
        out.println("<label style='display: inline-block'>");
        out.println(String.format("<p>%s</p>", label));
        printNumberInput(inputName, value);
        out.println("</label>");
    }

    private void printSelectInputWithLabel(
            String label, String inputName, ArrayList<String> options, String value
            ) {
        out.println("<label style='display: inline-block'>");
        out.println(String.format("<p>%s</p>", label));
        printSelectInput(options, inputName, value);
        out.println("</label>");
    }

    private void writeSubmit(String value) {
        out.println(String.format("<p><button type='submit'>%s</button></p>", value));
    }

    public void writeAddRowForm (ArrayList<String> coverTypes, ArrayList<String> pageLayouts) {
        beginDocument();
        writeH1("Add Row");
        out.println("<form action='' method='post' >");
        printTextInputWithLabel("Manufacture Name", "manufacturer_name", "");
        printTextInputWithLabel("Notepad Name", "notebook_name", "");
        printNumberInputWithLabel("Pages Count", "number_of_pages", "100");
        printSelectInputWithLabel("Cover Type", "cover_type", coverTypes, "");
        printTextInputWithLabel("Manufacture Country", "manufacturer_country", "");
        printSelectInputWithLabel("Page Layout", "page_layout", pageLayouts, "");
        writeSubmit("Add");
        out.println("</form>");
    }

    public void writeDeleteRowForm(ArrayList<String> options, String method, String selected) {
        beginDocument();
        String h1 = "Delete Row";
        String value = "Delete";
        if (method.equals("get")) {
            h1 = "Update Row";
            value = "Select";
        }
        writeH1(h1);
        out.println(String.format("<form action='' method='%s' >", method));
        printSelectInputWithLabel("Row Id", "row_id", options, selected);
        writeSubmit(value);
        out.println("</form>");
    }

    public void writeUpdateForm(Map<String, String> params,Map<String, ArrayList<String>> options) {
        if (params == null) {
            return;
        }

        out.println("<form action='' method='post' >");

        printHiddenInput("row_id", params.get("notebook_id"));

        printTextInputWithLabel(
                "Manufacture Name", "manufacturer_name", params.get("manufacturer_name")
        );

        printTextInputWithLabel(
                "Notepad Name", "notebook_name", params.get("notebook_name")
        );
        printNumberInputWithLabel(
                "Pages Count", "number_of_pages", params.get("number_of_pages")
        );

        printSelectInputWithLabel(
                "Cover Type", "cover_type", options.get("cover_type"), params.get("cover_type")
        );

        printTextInputWithLabel(
                "Manufacture Country", "manufacturer_country", params.get("manufacturer_country")
        );

        printSelectInputWithLabel(
                "Page Layout", "page_layout", options.get("page_layout"), params.get("page_layout")
        );

        writeSubmit("Update");
        out.println("</form>");
    }

    private void printHiddenInput(String name, String value) {
        out.println(String.format("<input type='hidden' name='%s' value='%s' />", name, value));
    }
}
