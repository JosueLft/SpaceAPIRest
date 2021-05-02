package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.repositories.WorkRepository;
import com.reign.lofty.space.services.exception.DatabaseException;
import com.reign.lofty.space.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    public List<Work> findAll() {
        return workRepository.findAll();
    }

    public Work findById(Long id) {
        Optional<Work> obj = workRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Work insert(Work obj) {
        return workRepository.save(obj);
    }

    public void delete(Long id) {
        try {
            workRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Work update(Long id, Work obj) {
        try {
            Work entity = workRepository.getOne(id);
            updateData(entity, obj);
            return workRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Work entity, Work obj) {
        entity.setTitle(obj.getTitle());
        entity.setCover(obj.getCover());
        entity.setStatus(obj.getStatus());
        entity.setSynopsis(obj.getSynopsis());
        entity.setDistributedBy(obj.getDistributedBy());
        entity.setGenre(obj.getGenre());
    }
}