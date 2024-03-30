package com.example.salesmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
