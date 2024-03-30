package com.example.salesmanagementsystem.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "saleItems")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @OneToOne
    private Product product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Min(value = 0, message = "Price cannot be negative")
    private double price;

    public SaleItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;

    }
}
