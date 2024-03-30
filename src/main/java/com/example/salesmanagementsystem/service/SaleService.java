package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.SaleRequest;
import com.example.salesmanagementsystem.dto.SaleUpdateRequest;
import com.example.salesmanagementsystem.model.Sale;

import java.util.List;

public interface SaleService {

    Sale createSale(SaleRequest saleRequest);

    List<Sale> findAllSales();

    Sale updateSale(Long id,SaleUpdateRequest updateRequest);
}
