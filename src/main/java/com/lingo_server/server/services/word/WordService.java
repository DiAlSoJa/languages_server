package com.lingo_server.server.services.word;

import java.util.List;
import java.util.Optional;

import com.lingo_server.server.dtos.WordDto;
import com.lingo_server.server.entities.WordEntity;

public interface WordService {
    List<WordEntity> findAll();
    Optional<WordEntity> findById(Long id);
    WordEntity save(WordEntity lng);
    Optional<WordEntity> update(Long id,WordEntity lng);
    boolean delete(Long id);
    List<WordEntity> findAllByLanguage(Long id);

    List<WordEntity> saveExcel(List<WordDto> words);
}
