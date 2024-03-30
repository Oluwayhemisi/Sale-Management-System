package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.ProductRequest;
import com.example.salesmanagementsystem.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);

    Product updateProduct(ProductRequest productRequest);

    Product findById(String id);

    List<Product> getAllProducts();

    String deleteProduct(String id);
}
