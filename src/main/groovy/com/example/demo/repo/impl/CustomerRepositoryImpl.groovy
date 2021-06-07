package com.example.demo.repo.impl

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Repository

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@Slf4j
@Repository
class CustomerRepositoryImpl implements CustomerRepository{

    @Override
    List<Customer> listCustomers(String searchKey) {
        log.info("invoke listCustomers in CustomerRepository...")
        def list = [
                new Customer([
                        name   : "java",
                        phoneNo: "1"
                ]),
                new Customer([
                        name   : "groovy",
                        phoneNo: "2"
                ])
        ]
        if(!searchKey){
            list
        }else{
            list.findAll{
                it.name?.contains(searchKey) || it.phoneNo?.contains(searchKey)
            }
        }
    }
}
