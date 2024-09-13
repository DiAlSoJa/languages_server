package com.lingo_server.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gardens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GardenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates ID
    private Long id;

    @Column(nullable = false)
    private String userId;  // Typically a reference to the user, could be a String if using an external system

    @Column(nullable = false)
    private String name;
}