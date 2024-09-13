package com.lingo_server.server.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;
import java.util.Set;
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

import com.lingo_server.server.dtos.ListDTO;
import com.lingo_server.server.entities.LanguageEntity;
import com.lingo_server.server.entities.ListEntity;
import com.lingo_server.server.entities.WordEntity;
import com.lingo_server.server.repositories.LanguageRepository;
import com.lingo_server.server.services.list.ListService;
import com.lingo_server.server.validation.ExistsDb;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/lists")
public class ListContoller {
    
    @Autowired
    private ListService listService;
   
    @Autowired 
    private LanguageRepository languageRepository;

    @Autowired
    private ExistsDb existsDb;

    // Obtener todos los lenguajes
    @GetMapping("")
    public ResponseEntity<List<ListEntity>> getAll() {
        List<ListEntity> lists = listService.findAll();
        return ResponseEntity.ok(lists);
    }

    // Obtener un lenguaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<ListEntity> getById(@PathVariable Long id) {
        Optional<ListEntity> language = listService.findById(id);
        
       if(language.isPresent()){
        return ResponseEntity.ok(language.orElseThrow());
       }
       return ResponseEntity.notFound().build();
    }

    // Crear un nuevo lenguaje
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ListDTO list, BindingResult result) {
    
        if (result.hasFieldErrors()) {
            return validation(result);
        }
    
        Optional<LanguageEntity> existLanguage = existsDb.findLanguageById(list.getLanguageId());
    
        if (!existLanguage.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Language not found");
        }
    
        // Creación de la entidad ListEntity usando el builder
        ListEntity newList = ListEntity.builder()
                .name(list.getName())
                .language(existLanguage.get())
                .build();
    

        Set<WordEntity> wordList = list.getWords().stream()
                .map(idWord -> existsDb.findWordById(idWord))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    
                System.out.println(list.getWords());

        newList.setWords(wordList);

        ListEntity listCreated = listService.save(newList);
    
        return ResponseEntity.status(HttpStatus.CREATED).body(listCreated);
    }




    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ListDTO list,BindingResult result,@PathVariable Long id) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<LanguageEntity> language = languageRepository.findById(list.getLanguageId());

        if(!language.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Language not found");
        }
 
     
        ListEntity newList = ListEntity.builder()
                .name(list.getName())
                .language(language.get())
                .build();
    

        Set<WordEntity> wordList = list.getWords().stream()
                .map(idWord -> existsDb.findWordById(idWord))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    
        newList.setWords(wordList);
        Optional<ListEntity> listCreated = listService.update(id,newList);
        return ResponseEntity.status(HttpStatus.CREATED).body(listCreated);

    
    }


    // Eliminar un lenguaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = listService.delete(id);
        
        if (deleted) {
            return ResponseEntity.noContent().build(); // Respuesta HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // Respuesta HTTP 404
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err->{
            errors.put(err.getField(), "Field '"+ err.getField()+ "' " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
