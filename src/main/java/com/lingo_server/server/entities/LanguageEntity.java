package com.lingo_server.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "languages")
@Data 
@NoArgsConstructor  
@AllArgsConstructor 
@Builder           
public class LanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 2)
    @Column(unique = true)
    private String locale;
}