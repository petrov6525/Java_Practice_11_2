package com.example.Java_Practice_11_2.Controllers;

import com.example.Java_Practice_11_2.repositories.CustomerRepository;
import com.example.Java_Practice_11_2.repositories.ProductSalesRepository;
import com.example.Java_Practice_11_2.repositories.SalesMenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Task2Controller {
    @GetMapping("/get-all-sales-men")
    public String getAllSalesMen(Model model) {

        model.addAttribute("salesMen", SalesMenRepository.getAllSalesMen());

        return "task2/all_sales_men";
    }

    @GetMapping("/get-all-customers")
    public String getAllCustomers(Model model) {

        model.addAttribute("customers", CustomerRepository.getAllCustomers());

        return "task2/all_customers";
    }

    @GetMapping("/get-all-products")
    public String getAllProducts(Model model) {

        model.addAttribute("products", ProductSalesRepository.getAllProducts());

        return "task2/all_products";
    }
}
