package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.entities.enums.WorkType;
import com.reign.lofty.space.services.interfaces.CoverStorage;
import com.reign.lofty.space.services.interfaces.ContentAccess;
import com.reign.lofty.space.services.interfaces.DeleteFile;
import com.reign.lofty.space.services.interfaces.SeparateDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecoverSaikaiNovel extends Work implements ContentAccess,
                                                        SeparateDescription,
                                                        CoverStorage,
                                                        DeleteFile {

    private Document doc;
    private String url = "https://saikaiscan.com.br/novels/reincarnation-of-the-strongest-sword-god-rssg/99";

    public RecoverSaikaiNovel() {
    }

    public RecoverSaikaiNovel(String url) {
        this.url = url;
    }

    public RecoverSaikaiNovel(Long id, String title, String synopsis, String genre, WorkType workType, String status, String distributedBy, byte[] cover) {
        super(id, title, synopsis, genre, workType, status, distributedBy, cover);
    }

    @Override
    public Work accessContent() {
        try {
            doc = Jsoup.connect(this.url).get();

            RecoverSaikaiNovel sn = new RecoverSaikaiNovel();
            sn.setTitle(doc.select("h2").text());
            File path = new File("src/main/resources/page/" + sn.getTitle());
            sn.setSynopsis(doc.select("div[class=summary-text]").text());
            sn.setGenre(description(doc, "div[class=info]", "nero"));
            sn.setStatus(description(doc, "div[class=info]", "Status"));
            sn.setDistributedBy("Saikai Scan");
            URL coverUrl = new URL("https://saikaiscan.com.br/" + doc.select("div.cover img").attr("data-src"));
            byte[] cover = recoverCover(coverUrl, sn.getTitle());
            sn.setCover(cover);
            sn.setType(WorkType.NOVEL);

//            deleteFile(path);

            return sn;
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

    @Override
    public boolean deleteFile(File path) {
        try {
            File f = path;
            f.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String description(Document doc, String tag, String regen) {
        String[] aux = new String[1];
        List<Element> types = doc.select(tag).stream()
                .filter((info) -> info.text().contains(regen))
                .collect(Collectors.toList());
        Stream<String> teste = Pattern.compile(": ").splitAsStream(types.get(0).text());
        teste.forEach(t -> aux[0] = t);

        return aux[0];
    }
}