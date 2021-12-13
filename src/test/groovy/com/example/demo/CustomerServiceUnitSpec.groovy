package com.example.demo

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import com.example.demo.service.impl.CustomerServiceImpl
import spock.lang.Specification

class CustomerServiceUnitSpec extends Specification{
    def testSpy(){
        given:
        CustomerServiceImpl customerService = Spy{
            aMethod(_) >> {
                key->
                    println "in spy $key"
            }
        }
        customerService.customerRepository = Mock(CustomerRepository)
        customerService.customerRepository.listCustomers(_)>>[new Customer(name:'jackson')]
        when:
        def customers = customerService.listCustomers("jack")
        then:
        customers.size() == 1
        customers[0].name == 'jackson'
    }
}
