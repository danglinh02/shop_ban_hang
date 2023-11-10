package com.example.Library.service;

import com.example.Library.dto.CustomerDto;
import com.example.Library.entity.Customer;
import org.springframework.security.core.parameters.P;

public interface CustomerService {
    public CustomerDto save(CustomerDto customerDto);
    public Customer findByUserName(String userName);
    public  Customer saveInfo(Customer customer);
}
