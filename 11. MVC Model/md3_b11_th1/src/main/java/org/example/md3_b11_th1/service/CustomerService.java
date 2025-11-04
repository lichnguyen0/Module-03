package org.example.md3_b11_th1.service;

import org.example.md3_b11_th1.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    void save (Customer customer);

    Customer findById(int id);

    void update (int id, Customer customer);

    void remove (int id);
}
