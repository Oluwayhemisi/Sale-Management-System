package com.example.salesmanagementsystem.repo;


import com.example.salesmanagementsystem.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    Boolean  existsByEmail(String email);
}

