package com.example.demo

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import com.example.demo.service.impl.CustomerServiceImpl
import spock.lang.Specification

class CustomerServiceUnitSpec extends Specification {
    CustomerServiceImpl customerService = Spy()
    CustomerRepository customerRepository = Mock()
    def setup(){
        customerService.customerRepository = customerRepository
    }
    /**
     * spock不支持mock静态方法。
     * 有两种解决方案：
     * 1 集成powermock
     * 2 抽取到非static方法中
     * */
    def testSpy() {
        given:
        customerService.customerRepository.listCustomers(_) >> [new Customer(name: 'jackson')]
        when:
        def customers = customerService.listCustomers("jack")
        then:
        customers.size() == 1
        customers[0].name == 'jackson'
        /**
         * 对于Mock、Stub、Spy的interaction verify，其mock方法要和该verify写在一起。！！！
         * 对于Mock、Stub、Spy的interaction verify，其mock方法要和该verify写在一起。！！！
         * 对于Mock、Stub、Spy的interaction verify，其mock方法要和该verify写在一起。！！！
         * 这个在官网上找不到，stackoverflow上有高人说是这样的。
         */
        1 * customerService.aMethod(_) >> {
            key ->
                println "in spy $key"
        }
    }
}
