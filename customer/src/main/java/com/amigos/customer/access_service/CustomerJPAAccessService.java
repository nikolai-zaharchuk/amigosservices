package com.amigos.customer.access_service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.entity.Customer;
import com.amigos.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository("jpa")
@AllArgsConstructor
public class CustomerJPAAccessService implements CustomerDao {
    private final CustomerRepository customerRepository;

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }
}
