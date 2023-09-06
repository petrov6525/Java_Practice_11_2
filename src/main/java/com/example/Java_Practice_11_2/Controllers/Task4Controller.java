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
import java.util.Map;

@Controller
public class Task4Controller {
    @GetMapping("/get-best-sales-man")
    public String getTheBestSalesMan(Model model) {

        Map<String, Object> result = ProductSalesRepository.getTheBestSalesMan();

        model.addAttribute("salesMan", result != null ? result.get("salesMan") : "");
        model.addAttribute("maxSum", result != null ? result.get("maxSum") : "");

        return "task4/the_best_sales_man";
    }

    @GetMapping("/get-best-customer")
    public String getTheBestCustomer(Model model) {

        Map<String, Object> result = ProductSalesRepository.getTheBestCustomer();

        model.addAttribute("customer", result != null ? result.get("customer") : "");
        model.addAttribute("maxSum", result != null ? result.get("maxSum") : "");

        return "task4/the_best_customer";
    }

    @GetMapping("/get-avg-sale-price")
    public String getAvgSalePrice(Model model) {
        model.addAttribute("avg", ProductSalesRepository.getAvgSalePrice());
        return "task4/avg_sale_price";
    }

    @GetMapping("/get-most-popular-product")
    public String getMostPopularProduct(Model model) {
        model.addAttribute("product", ProductSalesRepository.getMostPopularProduct());
        return "task4/most_popular_product";
    }
}
