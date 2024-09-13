package com.lingo_server.server.services.word;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingo_server.server.dtos.WordDto;
import com.lingo_server.server.entities.LanguageEntity;
import com.lingo_server.server.entities.WordEntity;
import com.lingo_server.server.repositories.LanguageRepository;
import com.lingo_server.server.repositories.WordRepository;

@Service
public class WordServiceImplementation implements WordService{
    @Autowired
    WordRepository wordRespository;
    @Autowired
    LanguageRepository lngRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WordEntity> findAll() {
        
        return wordRespository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WordEntity> findById(Long id) {
        return  wordRespository.findById(id);
        
    }

    @Override
    public WordEntity save(WordEntity word) {
        return wordRespository.save(word);
    }

    @Override
    public Optional<WordEntity> update(Long id, WordEntity word) {
        Optional<WordEntity> wordDb = wordRespository.findById(id);
        
        if (wordDb.isPresent()) {
            WordEntity wordFinal =wordDb.get();
            wordFinal.setTitle(word.getTitle());
            wordFinal.setLanguage(word.getLanguage());
            return Optional.of(wordRespository.save(wordFinal));
        } 
        return wordDb;
    }

    @Override
    public boolean delete(Long id) {
         Optional<WordEntity> wordDb = wordRespository.findById(id);
        
        if (wordDb.isPresent()) {
            wordRespository.delete(wordDb.get());
            return true; 
        } else {
            return false; 
        }
    }

    @Override
    public List<WordEntity> findAllByLanguage(Long id) {
        
        
        return wordRespository.findByLanguage(id);
    }

    @Override
    public List<WordEntity> saveExcel(List<WordDto> words) {
        List<WordEntity> savedWords = new ArrayList<>();


        for (WordDto wordRequest : words) {
            String word = wordRequest.getWord();
            String locale = wordRequest.getLanguageLocale();

            Optional<LanguageEntity> languageExist = lngRepository.findFirstByLocale(locale);
           
            if(!languageExist.isPresent()){
                continue;
            }
             WordEntity newWord = new WordEntity();
            newWord.setTitle(word);
            newWord.setLanguage(languageExist.get());

            wordRespository.save(newWord);
            savedWords.add(newWord);
        }

        return savedWords;
    }
    
}
