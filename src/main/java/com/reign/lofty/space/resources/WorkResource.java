package com.reign.lofty.space.resources;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}