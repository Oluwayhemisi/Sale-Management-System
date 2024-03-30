package com.example.salesmanagementsystem.service;


import com.example.salesmanagementsystem.dto.*;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.model.Sale;
import com.example.salesmanagementsystem.model.SaleItem;
import com.example.salesmanagementsystem.repo.ProductRepository;
import com.example.salesmanagementsystem.repo.SaleRepository;
import com.example.salesmanagementsystem.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public SalesReportResponse generateSalesReport(ReportRequest reportRequest) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // AdjustndDateString pattern if needed
//        LocalDate startDate = LocalDate.parse(startDateString, formatter);
//        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        List<Sale> sales = saleRepository.findAllByCreationDateBetween(reportRequest.getStartDate(),reportRequest.getEndDate());
        long totalSales = sales.size();
        double totalRevenue = sales.stream().mapToDouble(Sale :: getTotal).sum();

        Map<Product,Integer> productQuantities = new HashMap<>();
        for(Sale sale : sales){
            for(SaleItem saleItem : sale.getSaleItems()){
                productQuantities.put(saleItem.getProduct(),productQuantities.getOrDefault(saleItem.getProduct(),0) + saleItem.getQuantity());

            }
        }
        List<ProductQuantity> topProducts = productQuantities.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))                .limit(10) // Adjust limit as needed
                .map(entry -> new ProductQuantity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Calculate top performing sellers
        Map<Client, Double> sellerSales = new HashMap<>();
        for (Sale sale : sales) {
            sellerSales.put(sale.getSeller(), sellerSales.getOrDefault(sale.getSeller(), 0.0) + sale.getTotal());
        }
        List<SellerSales> topSellers = sellerSales.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))                .limit(10) // Adjust limit as needed
                .map(entry -> new SellerSales(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new SalesReportResponse(totalSales, totalRevenue, topProducts, topSellers);


    }

    @Override
    public ProductReport generateProductReport(ReportRequest reportRequest) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Adjust pattern if needed
//        LocalDate startDate = LocalDate.parse(startDateString, formatter);
//        LocalDate endDate = LocalDate.parse(endDateString, formatter);

        ProductReport report = new ProductReport();

        // Inventory Status
        List<Product> allProducts = productRepository.findAll();
        report.setInventoryStatus(getInventoryStatus(allProducts));

        // Sales Performance
        List<Sale> sales = saleRepository.findSalesByCreationDateBetween(reportRequest.getStartDate(), reportRequest.getEndDate());
        report.setSalesPerformance(getSalesPerformance(sales));

        // Pricing Analysis
        double averagePrice = calculateAveragePrice(allProducts);
        report.setAveragePrice(averagePrice);

        return report;
    }
    private Map<Long, Integer> getInventoryStatus(List<Product> products) {
        return products.stream().collect(Collectors.toMap(Product::getId, Product::getQuantity));
    }

    private Map<Long, Double> getSalesPerformance(List<Sale> sales) {
        return sales.stream().flatMap(sale -> sale.getSaleItems().stream())
                .collect(Collectors.groupingBy(item -> item.getProduct().getId(), Collectors.summingDouble(item -> item.getQuantity() * item.getPrice())));
    }

    private double calculateAveragePrice(List<Product> products) {
        OptionalDouble averagePrice = products.stream().mapToDouble(Product::getPrice).average();
        return averagePrice.isPresent() ? averagePrice.getAsDouble() : 0.0;
    }

    @Override
    public ClientReport generateClientReport(int limit) {
        List<Client> allClients = userRepository.findAll();
        long totalClients = allClients.size();
        List<Client> topSpenders = findTopSpenders(limit); // Assuming top 5 spenders
        Map<String, Long> clientLocationStats = getClientLocationStats();
        return new ClientReport(totalClients,topSpenders,clientLocationStats);
    }


    private List<Client> findTopSpenders(int limit) {
        List<Client> allClients = userRepository.findAll();
        return allClients.stream()
                .map(client -> {
                    double totalSpending = calculateTotalSpending(client.getId());
                    client.setTotalSpending(totalSpending); // Assuming a setter for totalSpending
                    return client;
                })
                .sorted(Comparator.comparingDouble(Client::getTotalSpending).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }


    private Map<String, Long> getClientLocationStats() {
        List<Client> clients = userRepository.findAll();
        return clients.stream()
                .collect(Collectors.groupingBy(Client::getLocation, Collectors.counting()));
    }



    private double calculateTotalSpending(Long clientId) {
        List<Sale> sales = saleRepository.findByClientId(clientId);
        return sales.stream().mapToDouble(Sale::getTotal).sum();
    }
    //    private Map<Client, Integer> getClientActivity() {
//        return null;
//    }


}
