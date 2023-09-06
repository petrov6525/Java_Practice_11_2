package com.example.Java_Practice_11_2.Models;

import java.util.Date;

public class Sale {
    public int Id;
    public Date Date;

    public Customer Customer;

    public SalesMan SalesMan;

    public Product Product;

    public void setCustomer(com.example.Java_Practice_11_2.Models.Customer customer) {
        Customer = customer;
    }

    public void setSalesMan(com.example.Java_Practice_11_2.Models.SalesMan salesMan) {
        SalesMan = salesMan;
    }

    public void setProduct(com.example.Java_Practice_11_2.Models.Product product) {
        Product = product;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
