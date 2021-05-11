package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.PageService;
import com.reign.lofty.space.services.functions.RecoverNeoxMangaPagesChapter;
import com.reign.lofty.space.services.functions.RecoverSaikaiMangaChapter;
import com.reign.lofty.space.services.functions.RecoverSaikaiMangaPagesChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/pages")
public class PageResource {

    @Autowired
    private PageService pageService;

    @GetMapping
    public ResponseEntity<List<Page>> findAll() {
        List<Page> list = pageService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Page> findById(@PathVariable Long id) {
        Page obj = pageService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Page> insert(@RequestBody Chapter chapter, String url) {
        List<Page> page = null;
        if(chapter.getWork().getDistributedBy().contains("Saikai")) {
            RecoverSaikaiMangaPagesChapter chapterPages = new RecoverSaikaiMangaPagesChapter(url);
            page = chapterPages.accessContent(chapter.getWork().getTitle());
        } else if(chapter.getWork().getDistributedBy().contains("Neox")) {
            RecoverNeoxMangaPagesChapter chapterPages = new RecoverNeoxMangaPagesChapter(url);
            page = chapterPages.accessContent(chapter.getWork().getTitle());
        }
        System.err.println(page.toString());
        for (Page p : page) {
            Page p1 = new Page(null, p.getPage(), p.getWorkChapterName(), p.getNumberPage(), chapter);
            pageService.insert(p1);

        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(chapter.getId())
                .toUri();
        return ResponseEntity.created(uri).body(page.get(0));
    }
}