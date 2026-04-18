package com.example.System.Entity;

import com.example.System.Enum.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GoogleAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String accessToken;

    @Column(unique = true)
    private String refreshToken;

    private Instant expiryTime;

    private Long userId;

    private RoleType role;

    private Instant createdAt;

    private Instant updatedAt;
}