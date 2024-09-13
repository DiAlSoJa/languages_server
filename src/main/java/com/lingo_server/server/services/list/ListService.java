package com.lingo_server.server.services.list;
import java.util.List;
import java.util.Optional;

import com.lingo_server.server.entities.ListEntity;


public interface ListService {
    List<ListEntity> findAll();
    Optional<ListEntity> findById(Long id);
    ListEntity save(ListEntity lng);
    Optional<ListEntity> update(Long id,ListEntity lng);
    boolean delete(Long id);
}
