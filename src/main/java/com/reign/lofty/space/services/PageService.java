package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.repositories.PageRepository;
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
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page findById(Long id) {
        Optional<Page> obj = pageRepository.findById(id);
        return obj.get();
    }

    public Page insert(Page obj) {
        return pageRepository.save(obj);
    }

    public void delete(Long id) {
        try {
            pageRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Page update(Long id, Page obj) {
        try {
            Page entity = pageRepository.getOne(id);
            updateData(entity, obj);
            return pageRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Page entity, Page obj) {
        entity.setPage(obj.getPage());
        entity.setWorkChapterName(obj.getWorkChapterName());
        entity.setNumberPage(obj.getNumberPage());
    }
}