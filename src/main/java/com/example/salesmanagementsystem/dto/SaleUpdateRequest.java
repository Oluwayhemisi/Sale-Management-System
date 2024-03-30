package com.example.salesmanagementsystem.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleUpdateRequest {
    private List<SaleItemUpdateRequest> saleItems;
}
