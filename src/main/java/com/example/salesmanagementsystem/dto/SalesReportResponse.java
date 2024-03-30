package com.example.salesmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SalesReportResponse {
    private long totalSales;
    private double totalRevenue;
    private List<ProductQuantity> topSellingProducts;
    private List<SellerSales> topPerformingSellers;
}
