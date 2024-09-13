package com.lingo_server.server.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "words")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generates ID
    private Long id;

    @NotEmpty
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageEntity language;

    @ManyToMany(mappedBy = "words",fetch = FetchType.EAGER)  // Inverse side of the relationship
    private Set<ListEntity> lists;  // Collection of lists containing this word

    @Builder.Default
    private String type="";
}