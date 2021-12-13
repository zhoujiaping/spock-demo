package com.example.demo.service.impl

import com.example.demo.Fns
import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import com.example.demo.service.CustomerService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Slf4j
@Service
class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository
    @Override
    List<Customer> listCustomers(String searchKey) {
        log.info("invoke listCustomers...")
        Fns.invoke(()->{
            aMethod(searchKey)
        })
        customerRepository.listCustomers(searchKey)
    }
    protected void aMethod(String key){
        log.info("amethod#$key")
    }

}
