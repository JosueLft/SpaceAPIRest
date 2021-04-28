package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.repositories.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return obj.get();
    }
}