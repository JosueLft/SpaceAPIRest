package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.services.interfaces.CoverStorage;
import com.reign.lofty.space.services.interfaces.ContentAccess;
import com.reign.lofty.space.services.interfaces.DeleteFile;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecoverNeoxManga extends Work implements ContentAccess,
                                                      CoverStorage {

    private Document doc;
    private String url = "https://neoxscans.net/manga/rtw-2347897123/";

    public RecoverNeoxManga() {
    }

    public RecoverNeoxManga(String url) {
        this.url = url;
    }

    public RecoverNeoxManga(Long id, String title, String synopsis, String genre, String type, String status, String distributedBy, byte[] cover) {
        super(id, title, synopsis, genre, type, status, distributedBy, cover);
    }

    @Override
    public Work accessContent() {
        try {
            doc = Jsoup.connect(this.url).get();

            RecoverNeoxManga nm = new RecoverNeoxManga();
            nm.setTitle(doc.select("h1").text());
            nm.setSynopsis(doc.select("div[class=\"summary__content \"]").text());
            nm.setGenre(doc.select("div[class=genres-content]").text());
            nm.setStatus(doc.select("div.summary-content").last().text());
            nm.setDistributedBy("Neox Scan");
            URL coverUrl = new URL(doc.select("div.summary_image img").attr("data-src"));
            byte[] cover = recoverCover(coverUrl, nm.getTitle());
            nm.setCover(cover);
            nm.setType("Manga");

            return nm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] recoverCover(URL coverUrl, String workName) {
        OutputStream output = null;
        try {
            BufferedImage imagem = null;
            imagem = ImageIO.read(coverUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(imagem, "JPEG", baos);
            byte[] cover = baos.toByteArray();
//            output = new FileOutputStream(path + "/" + workName + "Chapter.JPEG");
//            ImageIO.write(imagem, "JPEG", output);

            return cover;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}