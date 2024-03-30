package com.example.salesmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductReport {
    private Map<Long, Integer> inventoryStatus;
    private Map<Long, Double> salesPerformance;
    private double averagePrice;
}
