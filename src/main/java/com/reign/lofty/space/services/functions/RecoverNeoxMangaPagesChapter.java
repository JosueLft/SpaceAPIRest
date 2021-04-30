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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverNeoxMangaPagesChapter extends Work implements AccessPageContent,
                                                               PageStorage,
                                                               DeleteFile,
                                                               VerifyChapterTitle{

    Document doc;
    String url = "https://neoxscans.net/manga/o-comeco-depois-do-fim-89327/cap-103/";

    @Override
    public List<Page> accessContent(String workName) {
        try {
            Chapter chapter = new Chapter();
            Page p;
            doc = Jsoup.connect(url).get();
            /**
             * Não está funcionando a forma de recuperar a URL das paginas
             */
//            String imgs = doc.select("div[class=\"page-break no-gaps\"] img").attr("data-src");
            Elements imgs = doc.select("div[class=\"page-break no-gaps\"]");

            String chapterTitle = chapterTitle(doc.select("div.images-block img").attr("title"));
            File path = new File("src/main/resources/page/" + workName);

            imgs.forEach(img -> System.out.println(img.attr("src")));

//            for (Element img : imgs) {
//                p = new Page(
//                        page(
//                                new URL("https://saikaiscan.com.br/" + img.attr("src")),
//                                workName + chapterTitle + "Page" + img.attr("data-number"),
//                                path
//                        ),
//                        Integer.parseInt(img.attr("data-number"))
//                );
//                p.setWorkChapterName(workName + chapterTitle + "Page" + p.getNumberPage());
//                chapter.getPages().add(p);
//            }

//            deleteFile(path);
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public byte[] page(URL pageUrl, String workName, File path) {
        byte[] cover = new byte[1024];
        OutputStream output = null;
        try {
            path.mkdirs();
            HttpURLConnection connection = (HttpURLConnection) pageUrl.openConnection();
            InputStream input = connection.getInputStream();
            output = new FileOutputStream(path + "/" + workName + "Chapter.png");
            int read = 0;
            while ((read = input.read(cover)) != -1) {
                output.write(cover, 0, read);
            }
            return cover;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }

        return cover;
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

    @Override
    public boolean deleteFile(File path) {
        try {
            FileUtils.deleteDirectory(new File(String.valueOf(path)));
            System.out.println("Sucesso ao deletar!");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}