package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.ChapterService;
import com.reign.lofty.space.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/chapters")
public class ChapterResource {

    @Autowired
    private ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<Chapter>> findAll() {
        List<Chapter> list = chapterService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Chapter> findById(@PathVariable Long id) {
        Chapter obj = chapterService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}