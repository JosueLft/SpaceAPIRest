package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.services.interfaces.CoverStorage;
import com.reign.lofty.space.services.interfaces.ContentAccess;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecoverNeoxManga extends Work implements ContentAccess,
                                                      CoverStorage {

    private Document doc;
    private String url = "https://neoxscans.net/manga/t-4989797564478-m/";

    public RecoverNeoxManga() {
    }

    public RecoverNeoxManga(String url) {
        this.url = url;
    }

    public RecoverNeoxManga(Long id, String title, String synopsis, String genre, WorkType workType, String status, String distributedBy, byte[] cover) {
        super(id, title, synopsis, genre, workType, status, distributedBy, cover);
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
            nm.setType(WorkType.MANGA);

            return nm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] recoverCover(URL coverUrl, String workName) {
        byte[] cover = new byte[1024];
        OutputStream output = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) coverUrl.openConnection();
            InputStream input = connection.getInputStream();
            output = new FileOutputStream("src/main/resources/img/" + workName + "Cover.png");
            int read = 0;
            while ((read = input.read(cover)) != -1) {
                output.write(cover, 0, read);
            }
            return cover;
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
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
}