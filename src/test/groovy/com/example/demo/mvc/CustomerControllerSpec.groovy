package com.example.demo.mvc

import com.example.demo.model.Customer
import com.example.demo.repo.CustomerRepository
import com.example.demo.service.CustomerService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class CustomerControllerSpec extends Specification{
    @Autowired
    MockMvc mockMvc
    @Autowired
    CustomerService customerService

    void testCustomers() {
        given:"设置客户服务"
        def customerRepository = Mock(CustomerRepository)
        customerService.customerRepository = customerRepository

        customerRepository.listCustomers(_) >> [
                new Customer([
                        name:"scala",
                        phoneNo:"3"
                ])
        ]
        when:
        expect:
        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType("application/json")
                .param("searchKey","j"))
                actions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath('$[0].name')
                       .value("scala"))

        //或者下面这样，获得响应内容手动判断。
        //这样更方便定位问题
        /*
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType("application/json")
                .param("searchKey","j"))
                .andReturn()
        def body = mvcResult.response.contentAsString
        assert new ObjectMapper().readValue(body,new TypeReference<List<Customer>>() {})[0].name == 'scala'
       */
    }

}
