package com.amigos.customer.service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void register(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .password(customerRegistrationRequest.getPassword())
                .build();

        customerDao.insertCustomer(customer);

        System.out.println(customer);

    }
}
