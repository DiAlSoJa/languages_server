package com.lingo_server.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lingo_server.server.entities.LanguageEntity;

public interface LanguageRepository extends JpaRepository<LanguageEntity,Long> {
    
    Optional<LanguageEntity> findFirstByLocale(String locale);

}
