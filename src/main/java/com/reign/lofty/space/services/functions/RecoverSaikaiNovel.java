package com.reign.lofty.space.services.functions;

import com.reign.lofty.space.entities.Work;
import com.reign.lofty.space.services.interfaces.ContentAccess;
import com.reign.lofty.space.services.interfaces.CoverStorage;
import com.reign.lofty.space.services.interfaces.SeparateDescription;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecoverSaikaiNovel extends Work implements ContentAccess,
                                                        SeparateDescription,
                                                        CoverStorage {

    private Document doc;
    private String url = "https://saikaiscan.com.br/novels/reincarnation-of-the-strongest-sword-god-rssg/99";

    public RecoverSaikaiNovel() {
    }

    public RecoverSaikaiNovel(String url) {
        this.url = url;
    }

    public RecoverSaikaiNovel(Long id, String title, String synopsis, String genre, String type, String status, String distributedBy, String cover) {
        super(id, title, synopsis, genre, type, status, distributedBy, cover);
    }

    @Override
    public Work accessContent() {
        try {
            doc = Jsoup.connect(this.url).get();

            RecoverSaikaiNovel sn = new RecoverSaikaiNovel();
            sn.setTitle(doc.select("h2").text());
            sn.setSynopsis(doc.select("div[class=summary-text]").text());
            sn.setGenre(description(doc, "div[class=info]", "nero"));
            sn.setStatus(description(doc, "div[class=info]", "Status"));
            sn.setDistributedBy("Saikai Scan");
            URL coverUrl = new URL("https://saikaiscan.com.br/" + doc.select("div.cover img").attr("data-src"));
            String cover = recoverCover(coverUrl, sn.getTitle());
            sn.setCover(cover);
            sn.setType("Novel");

            return sn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String recoverCover(URL coverUrl, String workName) {
        OutputStream output = null;
        try {
            BufferedImage imagem = null;
            imagem = ImageIO.read(coverUrl);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(imagem, "JPEG", baos);
            byte[] cover = baos.toByteArray();
//            output = new FileOutputStream(path + "/" + workName + "Chapter.JPEG");
//            ImageIO.write(imagem, "JPEG", output);

            return new String(cover, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
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