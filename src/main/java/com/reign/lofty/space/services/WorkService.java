package com.reign.lofty.space.services;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    @Autowired
    private WorkRepository workRepository;

    public List<Work> findAll() {
        return workRepository.findAll();
    }

    public Work findById(Long id) {
        Optional<Work> obj = workRepository.findById(id);
        return obj.get();
    }
}