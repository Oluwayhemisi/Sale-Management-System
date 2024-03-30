package com.example.salesmanagementsystem.dto;

import com.example.salesmanagementsystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    private Long id;

    private String name;
    private String lastName;

    private String email;

    private String password;

    private Role roles;
    private String mobile;


    private String location;

    private double totalSpending;

}
