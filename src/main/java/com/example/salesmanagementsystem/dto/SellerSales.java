package com.example.salesmanagementsystem.dto;

import com.example.salesmanagementsystem.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SellerSales {
    private Client seller;
    private double totalSales;
}
