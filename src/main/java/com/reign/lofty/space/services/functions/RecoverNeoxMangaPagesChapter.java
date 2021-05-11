package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Chapter;
import com.reign.lofty.space.entities.Page;
import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.AccessPageContent;
import com.reign.lofty.space.services.interfaces.DeleteFile;
import com.reign.lofty.space.services.interfaces.PageStorage;
import com.reign.lofty.space.services.interfaces.VerifyChapterTitle;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverNeoxMangaPagesChapter extends Work implements AccessPageContent,
                                                               PageStorage,
                                                               VerifyChapterTitle{

    Document doc;
    String url;

    public RecoverNeoxMangaPagesChapter() {}

    public RecoverNeoxMangaPagesChapter(String url) {
        this.url = url;
    }

    @Override
    public List<Page> accessContent(String workName) {
        try {
            Chapter chapter = new Chapter();
            Page p;
            doc = Jsoup.connect(url).get();
            Elements imgs = doc.select("div[class=\"page-break no-gaps\"] img");

            String chapterTitle = doc.select("li.active").text();
            File path = new File("src/main/resources/page/" + workName);

            for (Element img : imgs) {
                p = new Page(
                        page(
                                new URL(img.attr("data-src")),
                                workName + chapterTitle + "Page" + img.id(),
                                path
                        ),
                        img.id()
                );
                p.setWorkChapterName(workName + chapterTitle + "Page" + p.getNumberPage());
                chapter.getPages().add(p);
            }
            return chapter.getPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public byte[] page(URL pageUrl, String workName, File path) {
        try {
            BufferedImage imagem = null;
            imagem = ImageIO.read(pageUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(imagem, "JPEG", baos);
            byte[] cover = baos.toByteArray();
            return cover;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
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