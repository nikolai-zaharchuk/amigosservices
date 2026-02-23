package com.amigos.customer.service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.dto.response.CustomerResponse;
import com.amigos.customer.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        customerDao.insert(customer);
    }

    public List<CustomerResponse> getCustomers() {
        List<Customer> customers = customerDao.getList();
        List<CustomerResponse> customerResponses = new ArrayList<>();

        customers.forEach(customer -> {
            customerResponses.add(new CustomerResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail()
            ));
        });

        return customerResponses;
    }

    public void truncate() {
        customerDao.truncate();
    }

    public void deleteCustomer(Long customerId) {
        customerDao.delete(customerId);
    }
}
