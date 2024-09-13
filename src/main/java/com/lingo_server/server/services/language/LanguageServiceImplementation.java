package com.lingo_server.server.services.language;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingo_server.server.entities.LanguageEntity;
import com.lingo_server.server.repositories.LanguageRepository;


@Service
public class LanguageServiceImplementation implements LanguageService{

    @Autowired
    LanguageRepository languageRepository;


    @Override
    @Transactional(readOnly = true)
    public List<LanguageEntity> findAll() {
        
        return (List<LanguageEntity>) languageRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LanguageEntity> findById(Long id) {
      
        return languageRepository.findById(id);
    }

    @Override
    @Transactional
    public LanguageEntity save(LanguageEntity lng) {
        
        return languageRepository.save(lng);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        
        Optional<LanguageEntity> lngDb = languageRepository.findById(id);
        
        if (lngDb.isPresent()) {
            languageRepository.delete(lngDb.get());
            return true; 
        } else {
            return false; 
        }
    }

    @Override
    public  Optional<LanguageEntity> update(Long id,LanguageEntity lng) {
        Optional<LanguageEntity> lngDb = languageRepository.findById(id);
        
        if (lngDb.isPresent()) {
            LanguageEntity lngFinal =lngDb.get();
            lngFinal.setName(lng.getName());
            lngFinal.setLocale(lng.getLocale());
            return Optional.of(languageRepository.save(lngFinal));
        } 
        return lngDb;
    }
    
}
