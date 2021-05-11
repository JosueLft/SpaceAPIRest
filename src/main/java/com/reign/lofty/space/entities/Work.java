package com.reign.lofty.space.entities;

import com.reign.lofty.space.entities.enums.WorkType;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_works")
public class Work implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 5000)
    private String synopsis;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false, length = 10)
    private String type;
    @Column(length = 25)
    private String status;
    @Column(nullable = false, length = 25)
    private String distributedBy;
    @Column(length = 1000000)
    private byte[] cover;

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY)
    private List<Chapter> chapters = new ArrayList<>();

    public Work() {}

    public Work(Long id, String title, String synopsis, String genre, String type, String status, String distributedBy) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        this.type = type;
        this.status = status;
        this.distributedBy = distributedBy;
    }

    public Work(Long id, String title, String synopsis, String genre, String type, String status, String distributedBy, byte[] cover) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        this.type = type;
        this.status = status;
        this.distributedBy = distributedBy;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDistributedBy() {
        return distributedBy;
    }
    public void setDistributedBy(String distributedBy) {
        this.distributedBy = distributedBy;
    }
    public byte[] getCover() {
        return cover;
    }
    public void setCover(byte[] cover) {
        this.cover = cover;
    }
    public List<Chapter> getChapters() {
        return chapters;
    }

    @Override
    public String toString() {
        return String.format("Titulo: %s" +
                        "\nTipo: %s" +
                        "\nStatus: %s" +
                        "\nDistribuido por: %s" +
                        "\nGênero: %s" +
                        "\nSinopse: %s\n\n" +
                        "Capitulos: \n\n" + this.getChapters(),
                title, type, status, distributedBy, genre, synopsis);
    }
}