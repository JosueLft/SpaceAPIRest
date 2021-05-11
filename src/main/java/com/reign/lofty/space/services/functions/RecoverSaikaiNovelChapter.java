package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.AccessChapterContent;
import com.reign.lofty.space.services.interfaces.VerifyChapterTitle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverSaikaiNovelChapter extends Work implements AccessChapterContent {

    private Document doc;
    private String url;

    public RecoverSaikaiNovelChapter() {}

    public RecoverSaikaiNovelChapter(String url) {
        this.url = url;
    }

    @Override
    public Chapter accessContent(Work work) {
        try {
            doc = Jsoup.connect(url).get();

            String chapterTitle = doc.select("h2.project-subtitle").text();
            String contentChapter = doc.select("h4.project-credits").text() + "\n\n\n";
            contentChapter += doc.select("div.full-text").text();
            Work w = new Work(work.getId(), work.getTitle(), work.getSynopsis(), work.getGenre(), work.getType(), work.getStatus(), work.getDistributedBy());
            Chapter chapter = new Chapter(null, chapterTitle, Instant.now(), contentChapter, w);
            return chapter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Chapter();
    }
}