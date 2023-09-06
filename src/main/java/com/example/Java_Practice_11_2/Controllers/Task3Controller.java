package com.example.Java_Practice_11_2.Controllers;

import com.example.Java_Practice_11_2.repositories.CustomerRepository;
import com.example.Java_Practice_11_2.repositories.ProductSalesRepository;
import com.example.Java_Practice_11_2.repositories.SalesMenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class Task3Controller {
    @GetMapping("/get-all-sales")
    public String getAllSales(Model model) {

        model.addAttribute("sales", ProductSalesRepository.getAllSales());

        return "task3/all_sales";
    }

    @GetMapping("/get-sales-by-date")
    public String getSalesByDate
            (Model model, @RequestParam(name = "date", required = false)String date) throws ParseException {
        model.addAttribute
                ("options", ProductSalesRepository.getDates());
        model.addAttribute
                ("sales", ProductSalesRepository.getSalesByDate(date));
        return "task3/sales_by_date";
    }

    @GetMapping("/get-sales-by-date-diapason")
    public String getSalesByDateDiapason
            (Model model, @RequestParam(name = "date_begin", required = false)String date_begin,
             @RequestParam(name = "date_end", required = false)String date_end) {

        ArrayList<Object> options = ProductSalesRepository.getDates();
        model.addAttribute("optionsBegin", options);
        model.addAttribute("optionsEnd", options);

        model.addAttribute
                ("sales", ProductSalesRepository.getSalesByDateDiapason(date_begin, date_end));
        return "task3/sales_by_date_diapason";
    }

    @GetMapping("/get-sales-by-sales-man")
    public String getSalesBySalesMan
            (Model model, @RequestParam(name = "sales_man", required = false)String sales_man) {

        model.addAttribute("options", SalesMenRepository.getSalesMenOptions("name"));

        model.addAttribute
                ("sales", ProductSalesRepository.getSalesBySalesMan(sales_man));
        return "task3/sales_by_sales_man";
    }

    @GetMapping("/get-sales-by-customer")
    public String getSalesByCustomer
            (Model model, @RequestParam(name = "customer", required = false)String customer) {

        model.addAttribute("options", CustomerRepository.getCustomersOptions("name"));

        model.addAttribute
                ("sales", ProductSalesRepository.getSalesByCustomer(customer));

        return "task3/sales_by_customer";
    }

}
