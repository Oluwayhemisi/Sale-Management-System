package com.example.salesmanagementsystem.controller;


import com.example.salesmanagementsystem.dto.ClientReport;

import com.example.salesmanagementsystem.dto.ProductReport;
import com.example.salesmanagementsystem.dto.ReportRequest;
import com.example.salesmanagementsystem.dto.SalesReportResponse;

import com.example.salesmanagementsystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;


    @PostMapping("/generate-sale-report")
    public ResponseEntity<SalesReportResponse> generateSalesReports(@Valid @RequestBody ReportRequest reportRequest){
        SalesReportResponse foundSaleReport=  reportService.generateSalesReport(reportRequest);
        return new ResponseEntity<>(foundSaleReport, HttpStatus.CREATED);
    }

    @GetMapping("/find-client-report/{limit}")
    public ResponseEntity<ClientReport> generateClientReport(@PathVariable("limit") int limit){
        ClientReport foundClientReport =  reportService.generateClientReport(limit);
        return new ResponseEntity<>(foundClientReport, HttpStatus.CREATED);
    }


    @GetMapping("/find-product-report")
    public ResponseEntity<ProductReport> generateProductReport(@Valid @RequestBody ReportRequest reportRequest){
        ProductReport foundProductReport =  reportService.generateProductReport(reportRequest);
        return new ResponseEntity<>(foundProductReport, HttpStatus.CREATED);
    }
}
