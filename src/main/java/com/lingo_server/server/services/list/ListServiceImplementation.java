package com.lingo_server.server.services.list;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingo_server.server.entities.ListEntity;
import com.lingo_server.server.repositories.LanguageRepository;
import com.lingo_server.server.repositories.ListRepository;


@Service
public class ListServiceImplementation implements ListService{

    @Autowired
    ListRepository listRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ListEntity> findAll() {
        
        return (List<ListEntity>) listRepository.findAll();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListEntity> findById(Long id) {
      
        return listRepository.findById(id);
    }

    @Override
    @Transactional
    public ListEntity save(ListEntity list) {

        return listRepository.save(list);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        
        Optional<ListEntity> listDb = listRepository.findById(id);
        
        if (listDb.isPresent()) {
            listRepository.delete(listDb.get());
            return true; 
        } else {
            return false; 
        }
    }

    @Override
    @Transactional
    public Optional<ListEntity> update(Long id, ListEntity newList) {
        // Busca la lista existente en la base de datos.
        Optional<ListEntity> existingListOpt = listRepository.findById(id);
        
        // Si la lista existe, procede con la actualización.
        if (existingListOpt.isPresent()) {
            ListEntity existingList = existingListOpt.get();
        
            // Limpia las palabras existentes y guarda el cambio inmediatamente para evitar conflictos.
            existingList.getWords().clear();
            listRepository.saveAndFlush(existingList);
            
            listRepository.deleteWordsByListId(id);
            listRepository.flush();
            // Asigna nuevos valores desde la entidad proporcionada.
            existingList.setName(newList.getName());
            existingList.setLanguage(newList.getLanguage());
            existingList.setWords(newList.getWords());
        
          
            return Optional.of(listRepository.save(existingList));
        }
        
        // Si no se encuentra la lista, simplemente retorna el Optional vacío.
        return Optional.empty();
    }
    
}
