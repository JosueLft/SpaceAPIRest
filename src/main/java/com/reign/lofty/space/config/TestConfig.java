package com.reign.lofty.space.config;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.repositories.ChapterRepository;
import com.reign.lofty.space.repositories.PageRepository;
import com.reign.lofty.space.repositories.WorkRepository;
import com.reign.lofty.space.services.functions.RecoverNeoxManga;
import com.reign.lofty.space.services.functions.RecoverSaikaiManga;
import com.reign.lofty.space.services.functions.RecoverSaikaiNovel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PageRepository pageRepository;

    RecoverSaikaiManga sm = new RecoverSaikaiManga();
    RecoverSaikaiNovel sn = new RecoverSaikaiNovel();
    RecoverNeoxManga nm = new RecoverNeoxManga();

    @Override
    public void run(String... args) throws Exception {
        Work saikaiManga = sm.accessContent();
        Work saikaiNovel = sn.accessContent();
        Work neoxManga = nm.accessContent();
        Work w1 = new Work(null, saikaiManga.getTitle(), saikaiManga.getSynopsis(), saikaiManga.getGenre(), saikaiManga.getType(), saikaiManga.getStatus(), saikaiManga.getDistributedBy(), saikaiManga.getCover());
        Work w2 = new Work(null, saikaiNovel.getTitle(), saikaiNovel.getSynopsis(), saikaiNovel.getGenre(), saikaiNovel.getType(), saikaiNovel.getStatus(), saikaiNovel.getDistributedBy(), saikaiNovel.getCover());
        Work w3 = new Work(null, neoxManga.getTitle(), neoxManga.getSynopsis(), neoxManga.getGenre(), neoxManga.getType(), neoxManga.getStatus(), neoxManga.getDistributedBy(), neoxManga.getCover());

        Chapter c1 = new Chapter(null, "T1",Instant.parse("2021-04-27T17:51:07Z"), "Chapter",w1);
        Chapter c2 = new Chapter(null, "T2",Instant.parse("2021-04-28T03:42:10Z"), "Chapter", w2);
        Chapter c3 = new Chapter(null, "T3",Instant.parse("2021-04-29T15:21:22Z"), "Chapter", w1);

        Page p1 = new Page(null, new byte[1], "1T1001", 1, c1);
        Page p2 = new Page(null, new byte[1], "1T1002", 2, c1);

        workRepository.saveAll(Arrays.asList(w1, w2, w3));
        chapterRepository.saveAll(Arrays.asList(c1, c2, c3));
        pageRepository.saveAll(Arrays.asList(p1,p2));
    }
}