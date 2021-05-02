package com.reign.lofty.space.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_chapters")
public class Chapter implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    private Instant moment;
    @Column(nullable = true)
    private String contentChapter;

    // composição
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @OneToMany(mappedBy = "chapter")
    private List<Page> pages = new ArrayList<>();

    public Chapter() {}

    public Chapter(Long id, String title, Instant moment, String contentChapter, Work work) {
        this.id = id;
        this.title = title;
        this.moment = moment;
        this.work = work;
        this.contentChapter = contentChapter;
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
    public Instant getMoment() {
        return moment;
    }
    public void setMoment(Instant moment) {
        this.moment = moment;
    }
    public Work getWork() {
        return work;
    }
    public void setWork(Work work) {
        this.work = work;
    }
    public String getContentChapter() {
        return contentChapter;
    }
    public void setContentChapter(String contentChapter) {
        this.contentChapter = contentChapter;
    }
    public List<Page> getPages() {
        return pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(id, chapter.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}