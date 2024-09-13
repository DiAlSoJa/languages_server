package com.lingo_server.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lingo_server.server.entities.RoleEntity;

public interface RoleRepository  extends JpaRepository<RoleEntity,Long>{
    Optional<RoleEntity> findByName(String name);
}
