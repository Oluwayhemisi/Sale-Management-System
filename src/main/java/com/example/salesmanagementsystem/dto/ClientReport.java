package com.example.salesmanagementsystem.dto;


import com.example.salesmanagementsystem.model.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ClientReport {
    private  long totalClients;
    private List<Client> topSpenders;
    private Map<String, Long> clientLocationStats;
//    private  Map<Client, Integer> clientActivity;
}
