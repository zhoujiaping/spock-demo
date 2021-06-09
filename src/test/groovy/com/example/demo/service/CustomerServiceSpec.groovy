package com.example.demo.service

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import com.example.demo.repo.impl.CustomerRepositoryImpl
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/07
 */
@SpringBootTest
@Slf4j
class CustomerServiceSpec extends Specification{
    @Autowired
    CustomerService customerService
    /**
     *
     * */
    void testListCustomers(){
        given:
        def customerRepository = Mock(CustomerRepository)
        customerService.customerRepository = customerRepository
        customerRepository.listCustomers(_)>>[
                new Customer.CustomerBuilder().name("scala").phoneNo("3")
        ]
        when:
        def customers = customerService.listCustomers("j")
        then:
        customers[0].name == 'scala'
    }
    void "测试获取客户列表"(){
        given:
        //不能spy接口，只能spy实现类
        def customerRepository = Spy(CustomerRepositoryImpl){
            listCustomers(_)>>{
                String searchKey->
                    log.info("invoke spy...")
                    callRealMethod()
            }
        }
        customerService.customerRepository = customerRepository

        def customers

        //多个when-then
        when:
        customers = customerService.listCustomers("j")
        then:
        customers[0].name == 'java'
        //该方法以"j"为参数被调用一次
        1 * customerRepository.listCustomers("j")

        when:
        customers = customerService.listCustomers("g")
        then:
        customers[0].name == 'groovy'
        1 * customerRepository.listCustomers(_)

    }

    void "测试获取客户列表table"(){
        given:
        //
        def customerRepository = Stub(CustomerRepositoryImpl){
            listCustomers(_)>>{
                String searchKey0->
                    log.info("invoke stub...{}",otherVar)
                    callRealMethod()
            }
        }
        customerService.customerRepository = customerRepository

        when:
        def customers = customerService.listCustomers(searchKey)

        then:
        customers.size() == resultSize
        //多组测试数据，多个断言结果
        where:
        searchKey||resultSize
        'j'||1
        ''||2
        'scala'||0
        otherVar = 'hello'
    }
}
