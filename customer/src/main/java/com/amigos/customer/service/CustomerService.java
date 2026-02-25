package com.amigos.customer.service;

import com.amigos.clients.fraud.FraudClient;
import com.amigos.clients.fraud.NotificationClient;
import com.amigos.clients.fraud.dto.request.NotificationRequest;
import com.amigos.clients.fraud.dto.response.FraudCheckResponse;
import com.amigos.clients.fraud.dto.response.NotificationResponse;
import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.dto.request.CustomerUpdateRequest;
import com.amigos.customer.dto.response.CustomerResponse;
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
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public CustomerService(
            @Qualifier("jpa") CustomerDao customerDao,
            RestTemplate restTemplate,
            FraudClient fraudClient,
            NotificationClient notificationClient
    ) {
        this.customerDao = customerDao;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
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

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        //send notification
        NotificationRequest notificationRequest = new NotificationRequest();
        NotificationResponse notificationResponse = notificationClient.sendNotification(notificationRequest);
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

    public void updateCustomer(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        customerDao.update(customerId, customerUpdateRequest);
    }
}
