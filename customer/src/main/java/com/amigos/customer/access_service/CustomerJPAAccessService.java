package com.amigos.customer.access_service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.dto.request.CustomerUpdateRequest;
import com.amigos.customer.entity.Customer;
import com.amigos.customer.exception.CustomerNotFoundException;
import com.amigos.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("jpa")
@AllArgsConstructor
public class CustomerJPAAccessService implements CustomerDao {
    private final CustomerRepository customerRepository;

    @Override
    public void insert(Customer customer) {
        customerRepository.saveAndFlush(customer);
    }

    @Override
    public List<Customer> getList() {
        return customerRepository.findAll();
    }

    @Override
    public void truncate() {
        customerRepository.deleteAll();
    }

    @Override
    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void update(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with %s not found".formatted(customerId));
        }

        boolean update = false;

        if (!customer.getFirstName().equals(customerUpdateRequest.getFirstName())) {
            update = true;
            customer.setFirstName(customerUpdateRequest.getFirstName());
        }

        if (!customer.getLastName().equals(customerUpdateRequest.getLastName())) {
            update = true;
            customer.setLastName(customerUpdateRequest.getLastName());
        }

        if (!customer.getEmail().equals(customerUpdateRequest.getEmail())) {
            update = true;
            customer.setEmail(customerUpdateRequest.getEmail());
        }

        if (update) {
            customerRepository.save(customer);
        }
    }
}
