package com.example.Library.service;

import com.example.Library.dto.CustomerDto;
import com.example.Library.entity.Customer;
import com.example.Library.entity.Role;
import com.example.Library.repository.CustomerRepository;
import com.example.Library.repository.RoleRepository;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;
    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer=new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(cryptPasswordEncoder.encode(customerDto.getPassword()));
        List<Role>roles=new ArrayList<>();
        Role role=new Role("CUSTOMER");
        roles.add(role);
        customer.setRoles(roles);
        Customer customerSave=customerRepository.save(customer);
        return mapperDto(customer);
    }

    @Override
    public Customer findByUserName(String userName) {
        return this.customerRepository.findByUsername(userName);
    }

    @Override
    public Customer saveInfo(Customer customer) {
        Customer customer1=customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setCountry(customer.getCountry());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customer1);
    }

    private CustomerDto mapperDto(Customer customer)
    {
        CustomerDto customerDto=new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        return customerDto;
    }

}
