package com.lingo_server.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lingo_server.server.entities.ListEntity;

public interface ListRepository extends JpaRepository<ListEntity,Long> {
    @Modifying
    @Query(value = "DELETE FROM list_words WHERE list_id = :listId", nativeQuery = true)
    void deleteWordsByListId(@Param("listId") Long listId);

}
