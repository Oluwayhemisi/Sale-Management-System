package com.example.salesmanagementsystem.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String userName;
   private String email;
    private String token;
}
