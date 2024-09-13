package com.lingo_server.server.services.language;
import java.util.List;
import java.util.Optional;


import com.lingo_server.server.entities.LanguageEntity;


public interface LanguageService {
    List<LanguageEntity> findAll();
    Optional<LanguageEntity> findById(Long id);
    LanguageEntity save(LanguageEntity lng);
    Optional<LanguageEntity> update(Long id,LanguageEntity lng);
    boolean delete(Long id);
}
