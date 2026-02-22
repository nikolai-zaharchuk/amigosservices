package com.amigos.customer.service;

import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public void register(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .password(customerRegistrationRequest.getPassword())
                .build();

        System.out.println(customer);

    }
}
