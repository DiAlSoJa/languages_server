package com.lingo_server.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lingo_server.server.entities.WordEntity;

@Repository
public interface WordRepository extends JpaRepository<WordEntity, Long> {

    @Query("SELECT w FROM WordEntity w WHERE w.language.id = :idLanguage")
    List<WordEntity> findByLanguage(@Param("idLanguage") Long idLanguage);
    
    
}