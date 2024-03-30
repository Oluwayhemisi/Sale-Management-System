package com.example.salesmanagementsystem.dto;

import com.example.salesmanagementsystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private Long id;


    private String name;
    private String lastName;
    private String mobile;

    private String email;

    private LocalDate dateJoined;

    private Role roles;
}
