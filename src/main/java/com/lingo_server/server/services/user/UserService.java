package com.lingo_server.server.services.user;

import java.util.List;
import java.util.Optional;

import com.lingo_server.server.entities.UserEntity;

public interface UserService {
    
    List<UserEntity> findAll();
    // Optional<UserEntity> findById();
    UserEntity save(UserEntity user);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
