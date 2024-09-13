package com.lingo_server.server.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingo_server.server.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;

@Component
public class GenerateToken {
    
    public  String generate(UserEntity user){
        Claims claims = Jwts.claims();
            // claims.put("authorities",  new ObjectMapper().writeValueAsString(user.getRoles()));
            claims.put("username", user.getUsername());
                
        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis()+360000))
            .setIssuedAt(new Date())
            .signWith(TokenJwtConfig.SECRET_KEY)
            .compact();

        return token;
    } 

  
}
