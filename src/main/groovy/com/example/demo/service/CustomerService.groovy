package com.example.demo.service

import com.example.demo.model.Customer


interface CustomerService {
    List<Customer> listCustomers(String searchKey)
}