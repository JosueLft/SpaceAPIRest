package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.AccessChapterContent;
import com.reign.lofty.space.services.interfaces.VerifyChapterTitle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverSaikaiMangaChapter extends Work implements AccessChapterContent,
                                                               VerifyChapterTitle{

    private Document doc;
    private String url;

    public RecoverSaikaiMangaChapter() {}

    public RecoverSaikaiMangaChapter(String url) {
        this.url = url;
    }

    @Override
    public Chapter accessContent(Work work) {
        try {
            doc = Jsoup.connect(url).get();

            String chapterTitle = chapterTitle(doc.select("div.images-block img").attr("title"));
            Work w = new Work(work.getId(), work.getTitle(), work.getSynopsis(), work.getGenre(), work.getType(), work.getStatus(), work.getDistributedBy());
            Chapter chapter = new Chapter(null, chapterTitle, Instant.now(), chapterTitle, w);
            return chapter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Chapter();
    }

    @Override
    public String chapterTitle(String attr) {
        List<String> chapterTitle;
        String title = null;
        if(attr.contains("-")) {
            chapterTitle = Pattern.compile("-").splitAsStream(attr).collect(Collectors.toList());
            title = chapterTitle.get(0);
            return title;
        } else if(attr.contains("#")) {
            chapterTitle = Pattern.compile("#").splitAsStream(attr).collect(Collectors.toList());
            title = chapterTitle.get(0);
            return title;
        }
        return attr;
    }
}