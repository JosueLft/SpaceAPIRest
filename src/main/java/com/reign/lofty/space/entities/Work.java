package com.reign.lofty.space.entities;

import com.reign.lofty.space.entities.enums.WorkType;

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
    @Column(nullable = false)
    private Integer type;
    @Column
    private String status;
    @Column(nullable = false)
    private String distributedBy;
    @Column(length = 1920)
    private byte[] cover;

    @OneToMany(mappedBy = "work")
    private List<Chapter> chapters = new ArrayList<>();

    public Work() {}

    public Work(Long id, String title, String synopsis, String genre, WorkType workType, String status, String distributedBy, byte[] cover) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        setType(workType);
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
    public WorkType getType() {
        return WorkType.valueOf(type);
    }
    public void setType(WorkType workType) {
        if(workType != null)
        this.type = workType.getCode();
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
                        "\nSinopse: %s\n\n",
                title, type, status, distributedBy, genre, synopsis);
    }
}