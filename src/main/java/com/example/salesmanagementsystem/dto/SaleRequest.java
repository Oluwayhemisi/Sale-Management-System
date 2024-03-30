package com.example.salesmanagementsystem.dto;

import com.example.salesmanagementsystem.model.SaleItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class SaleRequest {

    private Long clientId;
    private Long sellerId;
    private List<SaleItem> saleItems;
}
