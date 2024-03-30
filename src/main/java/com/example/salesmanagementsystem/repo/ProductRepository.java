package com.example.salesmanagementsystem.repo;

import com.example.salesmanagementsystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductTag(String id);
}
