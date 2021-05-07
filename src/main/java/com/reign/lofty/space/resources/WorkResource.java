package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.services.WorkService;
import com.reign.lofty.space.services.functions.RecoverNeoxManga;
import com.reign.lofty.space.services.functions.RecoverSaikaiManga;
import com.reign.lofty.space.services.functions.RecoverSaikaiNovel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping(value = "/works")
public class WorkResource {

    @Autowired
    private WorkService workService;

    @GetMapping
    public ResponseEntity<List<Work>> findAll() {
        List<Work> list = workService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Work> findById(@PathVariable Long id) {
        Work obj = workService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Work> insert(@RequestBody Work work) {
        if(work.getType().equalsIgnoreCase("manga")) {
            System.err.println(work.toString());
            if(work.getDistributedBy().contains("Saikai")) {
                RecoverSaikaiManga manga = new RecoverSaikaiManga(work.getTitle());
                work = manga.accessContent();
            } else if(work.getDistributedBy().contains("Neox")) {
                RecoverNeoxManga manga = new RecoverNeoxManga(work.getTitle());
                work = manga.accessContent();
            }
        } else if(work.getType().equalsIgnoreCase("novel")) {
            if(work.getDistributedBy().contains("Saikai")) {
                RecoverSaikaiNovel novel = new RecoverSaikaiNovel(work.getTitle());
                work = novel.accessContent();
            }
        }

        Work w1 = new Work(null, work.getTitle(), work.getSynopsis(), work.getGenre(), work.getType(), work.getStatus(), work.getDistributedBy(), work.getCover());
        work = workService.insert(w1);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(work.getId())
                .toUri();
        return ResponseEntity.created(uri).body(work);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Work> update(@PathVariable Long id, @RequestBody Work obj) {
        obj = workService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}