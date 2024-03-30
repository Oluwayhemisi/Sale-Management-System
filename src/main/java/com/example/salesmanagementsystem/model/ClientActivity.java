package com.example.salesmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

//@Getter
//@Setter
//@AllArgsConstructor
//@ToString
//@Entity
//@Table(name = "clientactivity")
//public class ClientActivity {
//    @Id
//    @Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Client client;
//
//    @Enumerated(EnumType.STRING)
//    private ClientActivityType activityType;  // Enum for different activity types
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date timestamp;  // Timestamp of the activity

    // Optional: Additional details (e.g., product viewed, purchase made)


//    public ClientActivity(Client client, ClientActivityType activityType) {
//        this.client = client;
//        this.activityType = activityType;
//        this.timestamp = new Date();  // Set timestamp on creation
//    }
//}
