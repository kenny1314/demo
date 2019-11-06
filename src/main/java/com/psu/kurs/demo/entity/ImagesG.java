package com.psu.kurs.demo.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "imagesg", schema = "cursovaya", catalog = "kurss")
public class ImagesG extends Images {

    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;

    @Type(type = "text")
    private String data;
    private String contentType;
    private String extension;

    //TODO genres
    @OneToOne(mappedBy = "imagesG")
    private Genres genres;

    public ImagesG() {
    }

    public ImagesG(Long id, String name, String data, String contentType, String extension) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.contentType = contentType;
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "ImagesG{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", extension='" + extension + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
