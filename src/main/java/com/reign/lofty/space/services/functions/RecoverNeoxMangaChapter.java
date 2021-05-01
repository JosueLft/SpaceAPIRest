package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.AccessChapterContent;
import com.reign.lofty.space.services.interfaces.VerifyChapterTitle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverNeoxMangaChapter extends Work implements AccessChapterContent {

    Document doc;
    String url = "https://neoxscans.net/manga/rtw-2347897123/cap-252/";

    @Override
    public Chapter accessContent(String workName) {
        try {
            Chapter chapter = new Chapter();
            doc = Jsoup.connect(url).get();

            String chapterTitle = doc.select("li.active").text();

            chapter.setTitle(chapterTitle);
            return chapter;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Chapter();
    }
}