package com.lingo_server.server.dtos;


import java.util.Set;
import java.util.HashSet;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListDTO {
    @NotBlank
    private String name;

    @NotNull
    private Long languageId;

    private Set<Long> words = new HashSet<>();
}