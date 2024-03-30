package com.example.salesmanagementsystem.config;


import com.example.salesmanagementsystem.model.Role;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@AllArgsConstructor
@Configuration
@Transactional
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {


    private final PasswordEncoder passwordEncoder;
    private  final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRepository.findByEmail("ismailoluwayemisi@gmail.com").isEmpty()){
            Client user = new Client("Oluwayemisi","ismailoluwayemisi@gmail.com","Ismail" ,"0987654321",passwordEncoder.encode("password1234#"), Role.ROLE_ADMIN);
            user.setDateJoined(LocalDate.now());


        }
    }
}
