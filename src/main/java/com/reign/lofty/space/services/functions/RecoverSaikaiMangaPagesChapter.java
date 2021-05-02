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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RecoverSaikaiMangaPagesChapter extends Work implements AccessPageContent,
                                                               PageStorage,
                                                               DeleteFile,
                                                               VerifyChapterTitle{

    Document doc;
    String url = "https://saikaiscan.com.br/manhuas/tales-of-demons-and-gods-tdg/post/capitulo-3225-derrubando-um-ao-outro/4499";

    @Override
    public List<Page> accessContent(String workName) {
        try {
            Chapter chapter = new Chapter();
            Page p;
            doc = Jsoup.connect(url).get();
            List<Element> imgs = doc.select("div.images-block img");

            String chapterTitle = chapterTitle(doc.select("div.images-block img").attr("title"));
            File path = new File("src/main/resources/page/" + workName);

            for (Element img : imgs) {
                p = new Page(
                        page(
                                new URL("https://saikaiscan.com.br/" + img.attr("src")),
                                workName + chapterTitle + "Page" + img.attr("data-number"),
                                path
                        ),
                        img.attr("data-number")
                );
                p.setWorkChapterName(workName + chapterTitle + "Page" + p.getNumberPage());
                chapter.getPages().add(p);
            }

//            deleteFile(path);
            return chapter.getPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public byte[] page(URL pageUrl, String workName, File path) {
        OutputStream output = null;
        try {
            BufferedImage imagem = null;
            imagem = ImageIO.read(pageUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(imagem, "JPEG", baos);
//            File f = path;
//            f.mkdir();
            byte[] cover = baos.toByteArray();
//            output = new FileOutputStream(path + "/" + workName + "Chapter.JPEG");
//            ImageIO.write(imagem, "JPEG", output);

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