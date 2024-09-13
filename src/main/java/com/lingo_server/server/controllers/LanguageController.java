package com.lingo_server.server.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

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

import com.lingo_server.server.entities.LanguageEntity;
import com.lingo_server.server.services.language.LanguageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    // Obtener todos los lenguajes
    @GetMapping("")
    public ResponseEntity<List<LanguageEntity>> getAll() {
        List<LanguageEntity> languages = languageService.findAll();
        return ResponseEntity.ok(languages);
    }

    // Obtener un lenguaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<LanguageEntity> getById(@PathVariable Long id) {
        Optional<LanguageEntity> language = languageService.findById(id);
        
       if(language.isPresent()){
        return ResponseEntity.ok(language.orElseThrow());
       }
       return ResponseEntity.notFound().build();
    }

    // Crear un nuevo lenguaje
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LanguageEntity languageEntity,BindingResult result) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        LanguageEntity createdLanguage = languageService.save(languageEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdLanguage);
    }




    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody LanguageEntity languageEntity,BindingResult result,@PathVariable Long id) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        Optional<LanguageEntity> existingLanguage = languageService.update(id,languageEntity);
        
        if (existingLanguage.isPresent()) {

            return ResponseEntity.status(HttpStatus.CREATED).body(existingLanguage.orElseThrow());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar un lenguaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = languageService.delete(id);
        
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
