package org.example.md3_b11_bt2.service;

import org.example.md3_b11_bt2.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void save(Product product);
    Product findById(int id);
    void remove(int id);
    void update(int id, Product product);
    List<Product> findByName(String name);
}
