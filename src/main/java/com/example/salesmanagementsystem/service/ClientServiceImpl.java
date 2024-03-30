package com.example.salesmanagementsystem.service;


import com.example.salesmanagementsystem.dto.ClientRequest;
import com.example.salesmanagementsystem.dto.ClientResponse;
import com.example.salesmanagementsystem.dto.UpdateDto;
import com.example.salesmanagementsystem.dto.UpdateResponse;
import com.example.salesmanagementsystem.exceptions.ClientException;
import com.example.salesmanagementsystem.model.Role;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService, UserDetailsService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;







    private void validateIfUserExist(ClientRequest userDto) throws ClientException {
        Optional<Client> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new ClientException("User already exist.", HttpStatus.CONFLICT);
        }
    }

    @Override
    public Client findUserByEmail(String email) throws ClientException {
        return userRepository.findByEmail(email).orElseThrow(() -> new ClientException("user does not exist", HttpStatus.NOT_FOUND));
    }



    @Override
    public ClientResponse register(ClientRequest userDto) throws ClientException {
    validateIfUserExist(userDto);
    Client user = new Client();
    user.setEmail(userDto.getEmail());
    user.setName(userDto.getName());
    user.setMobile(userDto.getMobile());
    user.setLastName(userDto.getLastName());
    user.setLocation(userDto.getLocation());
    user.setTotalSpending(userDto.getTotalSpending());
    user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
    user.setRoles(userDto.getRoles());
       Client savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, ClientResponse.class);
    }

    @Override
    public UpdateResponse updateClientDetails(UpdateDto updateDto) {
        Client updateClient = userRepository.findByEmail(updateDto.getEmail()).orElseThrow(()-> new ClientException("Client not found",HttpStatus.NOT_FOUND));
        Client savedClient = modelMapper.map(updateDto,Client.class);

        updateClient.setName(updateDto.getName());
        updateClient.setEmail(updateDto.getEmail());
        updateClient.setLastName(updateDto.getLastName());
        updateClient.setMobile(updateDto.getMobile());
        updateClient.setLocation(updateDto.getLocation());
        updateClient.setTotalSpending(updateDto.getTotalSpending());

        userRepository.save(updateClient);
        return modelMapper.map(savedClient,UpdateResponse.class);

    }

    @Override
    public List<Client> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String deleteClientById(String email) {
        Client client = userRepository.findByEmail(email).orElseThrow(()-> new ClientException("Client not found",HttpStatus.NOT_FOUND));
        userRepository.delete(client);
        return "Client with the email has been successfully Deleted";    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), getAuthorities(Collections.singleton(Role.ROLE_ADMIN)));
        }

        return null;


    }
    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }
}
