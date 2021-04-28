package com.reign.lofty.space.repositories;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}