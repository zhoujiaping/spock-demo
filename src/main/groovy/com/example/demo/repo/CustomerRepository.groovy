package com.example.demo.repo

import com.example.demo.model.Customer


interface CustomerRepository {
    List<Customer> listCustomers(String searchKey)
}
