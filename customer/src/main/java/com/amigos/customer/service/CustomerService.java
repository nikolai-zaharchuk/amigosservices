package com.amigos.customer.service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.dto.response.CustomerResponse;
import com.amigos.customer.dto.response.FraudCheckResponse;
import com.amigos.customer.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;
    private final RestTemplate restTemplate;

    public CustomerService(
            @Qualifier("jpa") CustomerDao customerDao,
            RestTemplate restTemplate
    ) {
        this.customerDao = customerDao;
        this.restTemplate = restTemplate;
    }

    public void register(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.getFirstName())
                .lastName(customerRegistrationRequest.getLastName())
                .email(customerRegistrationRequest.getEmail())
                .password(customerRegistrationRequest.getPassword())
                .build();

        //check if email is valid
        //check is email not taking

        customerDao.insert(customer);

        //send to fraud service
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8085/api/fraud-check/{customerId}",
                    FraudCheckResponse.class,
                    customer.getId()

        );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        //send notification



    }

    public List<CustomerResponse> getCustomers() {
        List<Customer> customers = customerDao.getList();
        List<CustomerResponse> customerResponses = new ArrayList<>();


        String gender = Math.random() * 10 > 5 ? "MALE" : "FEMALE";

        customers.forEach(customer -> {
            customerResponses.add(new CustomerResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                   33,
                    gender
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
