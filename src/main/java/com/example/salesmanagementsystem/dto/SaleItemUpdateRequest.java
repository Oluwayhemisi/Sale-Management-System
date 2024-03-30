package com.example.salesmanagementsystem.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleItemUpdateRequest {

    private Long id;
    private int quantity;
    private double price;
}
