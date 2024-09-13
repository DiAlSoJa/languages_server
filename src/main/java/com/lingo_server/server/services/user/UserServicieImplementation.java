package com.lingo_server.server.services.user;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingo_server.server.entities.RoleEntity;
import com.lingo_server.server.entities.UserEntity;
import com.lingo_server.server.repositories.RoleRepository;
import com.lingo_server.server.repositories.UserReponsitory;
import com.lingo_server.server.validation.ExistsDb;



@Service
public class UserServicieImplementation  implements UserService{

    @Autowired
    private UserReponsitory repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    @Transactional(readOnly=true)
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    // @Override
    // public Optional<UserEntity> findById() {
    //     throw new UnsupportedOperationException("Unimplemented method 'findById'");
    // }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {


        Optional<RoleEntity> optionalRoleUser = roleRepository.findByName("USER_ROLE");

        List<RoleEntity> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(role-> roles.add(role));

        if(user.isAdmin()){
            Optional<RoleEntity> optionlaRoleAdmin = roleRepository.findByName("ADMIN_ROLE");
            optionlaRoleAdmin.ifPresent((role->roles.add(role)));

        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode( user.getPassword() ) );

       return repository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        
      return repository.findByUsername(username);
    }
    
}
