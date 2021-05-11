package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.ChapterService;
import com.reign.lofty.space.services.functions.RecoverNeoxMangaChapter;
import com.reign.lofty.space.services.functions.RecoverSaikaiMangaChapter;
import com.reign.lofty.space.services.functions.RecoverSaikaiNovelChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/chapters")
public class ChapterResource {

    @Autowired
    private ChapterService chapterService;
    @Autowired
    PageResource pageResource;

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

    @PostMapping
    public ResponseEntity<Chapter> insert(@RequestBody Work work) {
        Chapter chapter = null;
        String url = work.getChapters().get(0).getTitle();
        if(work.getType().equalsIgnoreCase("Manga")) {
            if(work.getDistributedBy().contains("Saikai")) {
                RecoverSaikaiMangaChapter mangaChapter = new RecoverSaikaiMangaChapter(url);
                chapter = mangaChapter.accessContent(work);
            } else if(work.getDistributedBy().contains("Neox")) {
                RecoverNeoxMangaChapter mangaChapter = new RecoverNeoxMangaChapter(url);
                chapter = mangaChapter.accessContent(work);
            }
        } else if(work.getType().equalsIgnoreCase("Novel")) {
            if(work.getDistributedBy().contains("Saikai")) {
                RecoverSaikaiNovelChapter novelChapter = new RecoverSaikaiNovelChapter(url);
                chapter = novelChapter.accessContent(work);
            } else if(work.getDistributedBy().contains("Neox")) {

            }
        }

        Chapter c1 = new Chapter(null, chapter.getTitle(), chapter.getMoment(), chapter.getContentChapter(), chapter.getWork());
        System.err.println(c1);
        chapter = chapterService.insert(c1);
        pageResource.insert(chapter, url);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(chapter.getId())
                .toUri();
        return ResponseEntity.created(uri).body(chapter);
    }
}