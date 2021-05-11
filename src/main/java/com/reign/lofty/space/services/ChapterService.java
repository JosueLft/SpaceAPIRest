package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.repositories.ChapterRepository;
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
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    public Chapter findById(Long id) {
        Optional<Chapter> obj = chapterRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Chapter insert(Chapter obj) {
        return chapterRepository.save(obj);
    }

    public void delete(Long id) {
        try {
            chapterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Chapter update(Long id, Chapter obj) {
        try {
            Chapter entity = chapterRepository.getOne(id);
            updateData(entity, obj);
            return chapterRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Chapter entity, Chapter obj) {
        entity.setTitle(obj.getTitle());
        entity.setMoment(obj.getMoment());
        entity.setContentChapter(obj.getContentChapter());
        entity.setWork(obj.getWork());
    }
}