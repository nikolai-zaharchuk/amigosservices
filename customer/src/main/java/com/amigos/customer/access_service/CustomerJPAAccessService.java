package com.amigos.customer.access_service;

import com.amigos.customer.dao.CustomerDao;
import com.amigos.customer.entity.Customer;
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
        customerRepository.save(customer);
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

}
