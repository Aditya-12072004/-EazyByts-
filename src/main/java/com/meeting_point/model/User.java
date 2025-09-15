package com.meeting_point.model;

// Baaki code yahi rahega

import jakarta.persistence.*;
import lombok.Data; // Agar aapne Lombok dependency add ki hai

@Entity
@Data // Lombok annotation to generate getters/setters
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
}