package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.AccessChapterContent;
import com.reign.lofty.space.services.interfaces.DeleteFile;
import com.reign.lofty.space.services.interfaces.PageStorage;
import com.reign.lofty.space.services.interfaces.VerifyChapterTitle;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverSaikaiMangaChapter extends Work implements AccessChapterContent,
                                                               VerifyChapterTitle{

    Document doc;
    String url = "https://saikaiscan.com.br/manhuas/tales-of-demons-and-gods-tdg/post/capitulo-3225-derrubando-um-ao-outro/4499";

    @Override
    public Chapter accessContent(String workName) {
        try {
            Chapter chapter = new Chapter();
            doc = Jsoup.connect(url).get();

            String chapterTitle = chapterTitle(doc.select("div.images-block img").attr("title"));

            chapter.setTitle(chapterTitle);
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