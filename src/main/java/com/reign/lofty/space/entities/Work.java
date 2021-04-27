package com.reign.lofty.space.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Work implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String synopsis;
    private String genre;
    private String type;
    private String status;
    private String distributedBy;
    private byte[] cover;

    public Work() {}

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

    @Override
    public String toString() {
        return String.format("Titulo: %s" +
                        "\nTipo: %s" +
                        "\nStatus: %s" +
                        "\nDistribuido por: %s" +
                        "\nGênero: %s" +
                        "\nSinopse: %s\n\n",
                title, type, status, distributedBy, genre, synopsis);
    }
}