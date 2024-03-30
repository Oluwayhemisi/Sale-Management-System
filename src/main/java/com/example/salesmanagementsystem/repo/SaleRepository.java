package com.example.salesmanagementsystem.repo;

import com.example.salesmanagementsystem.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

   List<Sale> findAllByCreationDateBetween(LocalDate startDate, LocalDate endDate);

   List<Sale> findByClientId(Long clientId);

   List<Sale> findSalesByCreationDateBetween(LocalDate startDate,LocalDate endDate);

}
