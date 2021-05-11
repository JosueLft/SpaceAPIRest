package com.reign.lofty.space.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_chapter_page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100000000)
    private byte[] page;
    private String workChapterName;
    @Column(nullable = false, length = 10)
    private String numberPage;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public Page() {
    }

    public Page(byte[] page, String numberPage) {
        this.page = page;
        this.numberPage = numberPage;
    }

    public Page(Long id, byte[] page, String workChapterName, String numberPage, Chapter chapter) {
        this.id = id;
        this.page = page;
        this.workChapterName = workChapterName;
        this.numberPage = numberPage;
        this.chapter = chapter;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public byte[] getPage() {
        return page;
    }
    public void setPage(byte[] page) {
        this.page = page;
    }
    public Chapter getChapter() {
        return chapter;
    }
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    public String getWorkChapterName() {
        return workChapterName;
    }
    public void setWorkChapterName(String workChapterName) {
        this.workChapterName = workChapterName;
    }
    public String getNumberPage() {
        return numberPage;
    }
    public void setNumberPage(String numberPage) {
        this.numberPage = numberPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return String.format("Titulo: %s" +
                        "\nPagina: %s",
                workChapterName, numberPage);
    }
}