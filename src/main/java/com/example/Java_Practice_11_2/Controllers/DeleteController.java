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
public class DeleteController {
    @GetMapping("/delete-rows")
    public String deleteRows(Model model) {

        model.addAttribute("customers", CustomerRepository.getCustomersOptions("id"));
        model.addAttribute("salesMen", SalesMenRepository.getSalesMenOptions("id"));
        model.addAttribute("sales", ProductSalesRepository.getProductSalesOptions("id"));

        return "task5/delete_rows";
    }


    @PostMapping("/delete-customer")
    public String updateCustomer(HttpServletRequest request, Model model) {
        CustomerRepository.deleteCustomerById(request);

        return deleteRows(model);
    }


    @PostMapping("/delete-sales-man")
    public String updateSalesMan(HttpServletRequest request, Model model) {
        SalesMenRepository.deleteSalesManById(request);

        return deleteRows(model);
    }

    @PostMapping("/delete-product-sale")
    public String updateProductSale(HttpServletRequest request, Model model) {
        ProductSalesRepository.deleteProductSaleById(request);

        return deleteRows(model);
    }

}
