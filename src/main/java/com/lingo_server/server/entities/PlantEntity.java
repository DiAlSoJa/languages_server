package com.lingo_server.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates ID
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer basePrice;

    @Column(nullable = false)
    private String src;

    @Column(nullable = false)
    private String emoji;
    
}