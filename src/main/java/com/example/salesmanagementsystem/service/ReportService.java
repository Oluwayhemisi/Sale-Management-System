package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.ClientReport;
import com.example.salesmanagementsystem.dto.ProductReport;
import com.example.salesmanagementsystem.dto.ReportRequest;
import com.example.salesmanagementsystem.dto.SalesReportResponse;
import com.example.salesmanagementsystem.model.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportService {
    SalesReportResponse generateSalesReport(ReportRequest reportRequest);


     ProductReport generateProductReport(ReportRequest  reportRequest);
    ClientReport generateClientReport(int limit);

}
