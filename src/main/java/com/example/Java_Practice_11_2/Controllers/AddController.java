package com.example.Java_Practice_11_2.Controllers;

import com.example.Java_Practice_11_2.repositories.CustomerRepository;
import com.example.Java_Practice_11_2.repositories.ProductSalesRepository;
import com.example.Java_Practice_11_2.repositories.SalesMenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddController {
    @GetMapping("/add-rows")
    public String addRows(Model model) {

        model.addAttribute("action", "add");
        model.addAttribute("customers",CustomerRepository.getCustomersOptions("id"));
        model.addAttribute("salesMen", SalesMenRepository.getSalesMenOptions("id"));
        return "task5/add_rows";
    }


    @PostMapping("/add-customer")
    public String addCustomer(HttpServletRequest request, Model model)
    {
        CustomerRepository.addCustomer(request);

        return addRows(model);
    }

    @PostMapping("/add-sales-man")
    public String addSalesMan(HttpServletRequest request, Model model)
    {
        SalesMenRepository.addSalesMan(request);

        return addRows(model);
    }

    @PostMapping("/add-product-sale")
    public String addProductSale(HttpServletRequest request, Model model)
    {
        ProductSalesRepository.addProductSale(request);

        return addRows(model);
    }
}
