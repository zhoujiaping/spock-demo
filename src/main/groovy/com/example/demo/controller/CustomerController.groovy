package com.example.demo.controller

import com.example.demo.model.Customer
import com.example.demo.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class CustomerController {
    @Autowired
    CustomerService customerService
    @GetMapping("/customers")
    List<Customer> hello(@RequestParam(defaultValue = "") String searchKey) {
        def data = customerService.listCustomers(searchKey)
        data
    }
}
