package com.example.salesmanagementsystem.dto;


import com.example.salesmanagementsystem.model.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter

public class ClientReportResponse {
    private long totalClients;
    private List<Client> topSpendingClients;
    private Map<Client, Integer> clientActivity;
    private Map<String, Long> clientLocations;
}
