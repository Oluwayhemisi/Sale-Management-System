package com.example.salesmanagementsystem.dto;

import com.example.salesmanagementsystem.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductQuantity {

    private Product product;
    private int quantitySold;
}
