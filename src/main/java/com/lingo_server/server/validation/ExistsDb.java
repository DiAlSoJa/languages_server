package com.lingo_server.server.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lingo_server.server.entities.LanguageEntity;
import com.lingo_server.server.entities.ListEntity;
import com.lingo_server.server.entities.UserEntity;
import com.lingo_server.server.entities.WordEntity;
import com.lingo_server.server.repositories.LanguageRepository;
import com.lingo_server.server.repositories.ListRepository;
import com.lingo_server.server.repositories.UserReponsitory;
import com.lingo_server.server.repositories.WordRepository;

@Component
public class ExistsDb {

    @Autowired
    WordRepository wordRepository;

    @Autowired
    ListRepository listRepository;

    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    UserReponsitory userReponsitory;

    public Optional<WordEntity> findWordById(Long id) {
        return wordRepository.findById(id);
    }

    // Método para obtener una lista por su ID
    public Optional<ListEntity> findListById(Long id) {
        return listRepository.findById(id);
    }

    // Método para obtener un lenguaje por su ID
    public Optional<LanguageEntity> findLanguageById(Long id) {
        return languageRepository.findById(id);
    }

    public boolean existByUsername(String username ){

        return userReponsitory.existsByUsername(username);
    }

    public boolean existByEmail(String email ){

        return userReponsitory.existsByEmail(email);
    }
}