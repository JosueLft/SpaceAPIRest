package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}