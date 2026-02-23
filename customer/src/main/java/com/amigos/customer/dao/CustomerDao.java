package com.amigos.customer.dao;

import com.amigos.customer.entity.Customer;

import java.util.List;

public interface CustomerDao {
    void insert(Customer customer);
    List<Customer> getList();
    void truncate();
    void delete(Long customerId);
}
