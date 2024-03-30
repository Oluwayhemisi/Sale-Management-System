package com.example.salesmanagementsystem.repo;

import com.example.salesmanagementsystem.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
