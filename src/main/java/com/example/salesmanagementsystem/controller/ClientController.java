package com.example.salesmanagementsystem.controller;


import com.example.salesmanagementsystem.dto.*;
import com.example.salesmanagementsystem.exceptions.ClientException;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.service.ClientService;
import com.example.salesmanagementsystem.usersecurity.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;




    @PostMapping("/register")
    public ResponseEntity<ClientResponse> Register(@Valid @RequestBody ClientRequest userDto){
        ClientResponse response = clientService.register(userDto);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequest loginDto) throws ClientException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        Client user = clientService.findUserByEmail(loginDto.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setUserName(user.getName());
        loginResponseDto.setEmail(user.getEmail());
        loginResponseDto.setToken(token);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponse> updateClientDetails(@Valid @RequestBody UpdateDto updateDto){
        UpdateResponse response = clientService.updateClientDetails(updateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/get-all-clients")
    public ResponseEntity<?> getAllClients(){

        List<Client> response = clientService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClientDetails(@Valid @PathVariable("id") String id){
        String response = clientService.deleteClientById(id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
