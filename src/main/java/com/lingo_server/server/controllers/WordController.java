package com.lingo_server.server.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lingo_server.server.dtos.WordDto;
import com.lingo_server.server.entities.WordEntity;
import com.lingo_server.server.services.word.WordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/words")
public class WordController {

    @Autowired
    WordService wordService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<WordEntity> words = wordService.findAll();
        return ResponseEntity.status(200).body(words);
    } 

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<WordEntity> wordDB = wordService.findById(id);
        if(wordDB.isPresent()){
            return ResponseEntity.status(200).body(wordDB);
        }
        return ResponseEntity.notFound().build();
    } 
    @GetMapping("language/{idlanguage}")
    public ResponseEntity<?> getByLanguage(@PathVariable Long idlanguage){

        List<WordEntity> words = wordService.findAllByLanguage(idlanguage);

        return ResponseEntity.status(HttpStatus.OK).body(words);
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody WordEntity word,BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        WordEntity newWord = wordService.save(word);

        return ResponseEntity.status(HttpStatus.CREATED).body(newWord);
    } 

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody WordEntity word,BindingResult result,@PathVariable Long id) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<WordEntity> newWord = wordService.update(id,word);
        if(newWord.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(newWord.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    } 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = wordService.delete(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @PostMapping("excel")
    public ResponseEntity<?> excelWords(@RequestBody List<WordDto> words){
      
        List<WordEntity> savedWords= wordService.saveExcel(words);

        return ResponseEntity.ok(savedWords);
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "Field '"+ err.getField()+ "' " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}