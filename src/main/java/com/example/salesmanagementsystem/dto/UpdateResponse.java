package com.example.salesmanagementsystem.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class UpdateResponse {

    private String name;
    private String lastName;
    private String mobile;
    private String email;
    private String password;
}
