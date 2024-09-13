package com.lingo_server.server.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plant_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates ID
    private Long id;

    @Column(nullable = false)
    private String userId;  // Stored as a String, assuming it's an external ID

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "garden_id", nullable = false)  // Foreign key reference to GardenEntity
    private GardenEntity garden;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false)
    private int position;
}