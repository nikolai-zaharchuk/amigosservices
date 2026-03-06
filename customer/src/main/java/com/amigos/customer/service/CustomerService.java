package com.amigos.customer.service;

import com.amigos.amqp.config.RabbitMQMessageProducer;
import com.amigos.clients.fraud.FraudClient;
import com.amigos.clients.notification.NotificationClient;
import com.amigos.clients.fraud.dto.response.FraudCheckResponse;
import com.amigos.clients.notification.dto.request.NotificationRequest;
import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerRegistrationRequest;
import com.amigos.customer.dto.request.CustomerUpdateRequest;
import com.amigos.customer.dto.response.CustomerResponse;
import com.amigos.customer.entity.Customer;
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
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public CustomerService(
            @Qualifier("jpa") CustomerDao customerDao,
            RestTemplate restTemplate,
            FraudClient fraudClient,
            NotificationClient notificationClient,
            RabbitMQMessageProducer rabbitMQMessageProducer
    ) {
        this.customerDao = customerDao;
        this.restTemplate = restTemplate;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    public Customer register(CustomerRegistrationRequest customerRegistrationRequest) {
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
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .toCustomerEmail(customer.getEmail())
                .toCustomerId(customer.getId())
                .message("New customer has been registered int the our amazing service")
                .build();
       // NotificationResponse notificationResponse = notificationClient.sendNotification(notificationRequest);

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        return customer;
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
