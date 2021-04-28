package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private PageRepository PageRepository;

    public List<Page> findAll() {
        return PageRepository.findAll();
    }

    public Page findById(Long id) {
        Optional<Page> obj = PageRepository.findById(id);
        return obj.get();
    }
}