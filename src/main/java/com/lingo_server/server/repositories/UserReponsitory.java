package com.lingo_server.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lingo_server.server.entities.UserEntity;

public interface UserReponsitory extends JpaRepository<UserEntity,Long>{

    boolean existsByUsername(String username);    

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
} 
