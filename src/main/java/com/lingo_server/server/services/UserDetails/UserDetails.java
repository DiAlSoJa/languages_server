package com.lingo_server.server.services.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingo_server.server.entities.UserEntity;
import com.lingo_server.server.repositories.UserReponsitory;


@Service
public class UserDetails implements UserDetailsService{

    @Autowired
    private UserReponsitory repository;

    @Transactional(readOnly = true)
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        
            Optional<UserEntity> userOptional = repository.findByUsername(username);
            if(!userOptional.isPresent()){
                throw new UsernameNotFoundException(String.format("Username no existe en el sistema", username));
            }
            UserEntity user = userOptional.orElseThrow();

            List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());    
            
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
            );

            
    }
    
}
