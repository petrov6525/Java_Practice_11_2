package com.example.Java_Practice_11_2.Controllers;

import com.example.Java_Practice_11_2.Models.Sale;
import com.example.Java_Practice_11_2.repositories.CustomerRepository;
import com.example.Java_Practice_11_2.repositories.ProductSalesRepository;
import com.example.Java_Practice_11_2.repositories.SalesMenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UpdateController {
    @GetMapping("/update-rows")
    public String updateRows(Model model) {

        model.addAttribute("action", "update");
        model.addAttribute("customers", CustomerRepository.getCustomersOptions("id"));
        model.addAttribute("salesMen", SalesMenRepository.getSalesMenOptions("id"));
        model.addAttribute("sales", ProductSalesRepository.getProductSalesOptions("id"));

        return "task5/update_rows";
    }

    @GetMapping("/get-customer")
    public String getCustomerById(int id, Model model) {
        model.addAttribute("customer", CustomerRepository.getCustomerById(id));

        return updateRows(model);
    }

    @PostMapping("/update-customer")
    public String updateCustomer(HttpServletRequest request, Model model) {
        CustomerRepository.updateCustomer(request);

        return updateRows(model);
    }


    @GetMapping("/get-sales-man")
    public String getSalesManById(int id, Model model) {
        model.addAttribute("salesMan", SalesMenRepository.getSalesManById(id));

        return updateRows(model);
    }

    @PostMapping("/update-sales-man")
    public String updateSalesMan(HttpServletRequest request, Model model) {
        SalesMenRepository.updateSalesMan(request);

        return updateRows(model);
    }

    @GetMapping("/get-product-sale")
    public String getProductSaleById(int id, Model model) {
        model.addAttribute("sale", ProductSalesRepository.getSaleById(id));
        return updateRows(model);
    }

    @PostMapping("/update-product-sale")
    public String updateProductSale(HttpServletRequest request, Model model) {
        ProductSalesRepository.updateSale(request);

        return updateRows(model);
    }

}
