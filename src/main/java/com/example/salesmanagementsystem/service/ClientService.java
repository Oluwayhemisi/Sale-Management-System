package com.example.salesmanagementsystem.service;



import com.example.salesmanagementsystem.dto.ClientRequest;
import com.example.salesmanagementsystem.dto.ClientResponse;
import com.example.salesmanagementsystem.dto.UpdateDto;
import com.example.salesmanagementsystem.dto.UpdateResponse;
import com.example.salesmanagementsystem.exceptions.ClientException;
import com.example.salesmanagementsystem.model.Client;

import java.util.List;

public interface ClientService {

     Client findUserByEmail(String email) throws ClientException;

    ClientResponse register(ClientRequest userDto) throws ClientException;

    UpdateResponse updateClientDetails(UpdateDto updateDto);

    List<Client> getAllUsers();

    String deleteClientById(String id);


}
