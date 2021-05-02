package com.reign.lofty.space.config;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.repositories.ChapterRepository;
import com.reign.lofty.space.repositories.PageRepository;
import com.reign.lofty.space.repositories.WorkRepository;
import com.reign.lofty.space.services.functions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private WorkRepository workRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PageRepository pageRepository;

    RecoverSaikaiManga sm = new RecoverSaikaiManga("https://saikaiscan.com.br/manhuas/tales-of-demons-and-gods-tdg/9");
    RecoverSaikaiNovel sn = new RecoverSaikaiNovel("https://saikaiscan.com.br/novels/reincarnation-of-the-strongest-sword-god-rssg/99");
    RecoverNeoxManga nm = new RecoverNeoxManga("https://neoxscans.net/manga/rtw-2347897123/");

    RecoverSaikaiMangaChapter smc = new RecoverSaikaiMangaChapter();
    RecoverSaikaiMangaPagesChapter smpc = new RecoverSaikaiMangaPagesChapter();
    RecoverNeoxMangaChapter nmc = new RecoverNeoxMangaChapter();
    RecoverNeoxMangaPagesChapter nmpc = new RecoverNeoxMangaPagesChapter();

    @Override
    public void run(String... args) throws Exception {
        Work saikaiManga = sm.accessContent();
        Work saikaiNovel = sn.accessContent();
        Work neoxManga = nm.accessContent();

        Work w1 = new Work(null, saikaiManga.getTitle(), saikaiManga.getSynopsis(), saikaiManga.getGenre(), saikaiManga.getType(), saikaiManga.getStatus(), saikaiManga.getDistributedBy(), saikaiManga.getCover());
        Work w2 = new Work(null, saikaiNovel.getTitle(), saikaiNovel.getSynopsis(), saikaiNovel.getGenre(), saikaiNovel.getType(), saikaiNovel.getStatus(), saikaiNovel.getDistributedBy(), saikaiNovel.getCover());
        Work w3 = new Work(null, neoxManga.getTitle(), neoxManga.getSynopsis(), neoxManga.getGenre(), neoxManga.getType(), neoxManga.getStatus(), neoxManga.getDistributedBy(), neoxManga.getCover());

        Chapter saikaiMangaChapter = smc.accessContent(w1.getTitle());
        Chapter neoxMangaChapter = nmc.accessContent(w3.getTitle());

        List<Page> saikaiPages = smpc.accessContent(w1.getTitle());
        List<Page> neoxPages = nmpc.accessContent(w3.getTitle());

        Chapter c1 = new Chapter(null, saikaiMangaChapter.getTitle(), Instant.now(), "Manga Chapter", w1);
        Chapter c2 = new Chapter(null, neoxMangaChapter.getTitle(), Instant.parse("2021-04-28T03:42:10Z"), "Manga Chapter", w3);
        Chapter c3 = new Chapter(null, "T3", Instant.parse("2021-04-29T15:21:22Z"), "Manga Chapter", w1);

        workRepository.saveAll(Arrays.asList(w1, w2, w3));
        chapterRepository.saveAll(Arrays.asList(c1, c2, c3));

        for (Page page : saikaiPages) {
            Page p1 = new Page(null, page.getPage(), page.getWorkChapterName(), page.getNumberPage(), c1);
            pageRepository.saveAll(Arrays.asList(p1));
        }

        for (Page page : neoxPages) {
            Page p1 = new Page(null, page.getPage(), page.getWorkChapterName(), page.getNumberPage(), c2);
            pageRepository.saveAll(Arrays.asList(p1));
        }
    }
}