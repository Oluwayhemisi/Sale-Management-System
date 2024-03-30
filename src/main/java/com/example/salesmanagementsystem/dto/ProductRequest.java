package com.example.salesmanagementsystem.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductRequest {

    private Long id;

    private String name;

    private String description;

    private String category;

    private Integer quantity;

    private double price;
    private String productTag;
}
