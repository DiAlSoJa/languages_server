package com.lingo_server.server.controllers;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lingo_server.server.entities.UserEntity;
import com.lingo_server.server.security.GenerateToken;
import com.lingo_server.server.services.user.UserService;
import com.lingo_server.server.validation.ExistsDb;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private ExistsDb existsDb;
    
    @Autowired
    private GenerateToken generateToken;

    

    @GetMapping
    public List<UserEntity> list(){
        return service.findAll();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserEntity user,BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        
        if (existsDb.existByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "El nombre de usuario ya existe"));
        }
    
        if (existsDb.existByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "El correo electrónico ya está registrado"));
        }

       UserEntity userSaved=service.save(user);

       String token = generateToken.generate(userSaved);

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", userSaved.getUsername());
        body.put("message", String.format("Hola %s has regustrado con exito!", userSaved.getUsername()));


        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody UserEntity user,BindingResult result){
        user.setAdmin(false);
        return create(user, result);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken() {
        
        
       
        return ResponseEntity.ok("si hay token"); // Token válido
        
    }

    @GetMapping("/getme")
    public ResponseEntity<?> getMe() {
    
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  
        

    
        Optional<UserEntity> userDb = service.findByUsername(username);
    
        if (!userDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    
        // Obtener los datos del usuario
        UserEntity user = userDb.get();
    
        // Construir la respuesta con los datos del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
    
        return ResponseEntity.ok(response);
    }
    

    private ResponseEntity<?> validation(BindingResult result) {
        List<String> errors = new ArrayList<>();
        result.getFieldErrors().forEach(err -> {
            errors.add("Field '" + err.getField() + "' " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }
}
