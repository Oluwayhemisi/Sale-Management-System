package com.example.salesmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "my_client")
public class Client {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String mobile;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDate dateJoined;

    private String location;

    // Optional: Total spending (calculated or fetched)
    private double totalSpending;  // Assuming a way to calculate or retrieve total spending

    // Optional: Client activity (if tracked)
//    @OneToMany(mappedBy = "client")
//    private List<ClientActivity> activities;



    @Enumerated(value = EnumType.STRING)
   private Role roles;

    public Client(String name, String email, String lastName, String mobile, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
        this.mobile = mobile;
        if (roles == null) {
            roles = role;
        }
    }
}
