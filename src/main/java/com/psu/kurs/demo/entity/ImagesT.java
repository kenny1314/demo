package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "imagest", schema = "cursovaya", catalog = "kurss")
public class ImagesT extends Images {

    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;

    @Type(type = "text")
    private String data;
    private String contentType;
    private String extension;

    @OneToOne(mappedBy = "imagesT")
    private Platforms platforms;


    public ImagesT() {
    }

    public ImagesT(Long id, String name, String data, String contentType, String extension) {
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

    //чтоб данные картинки не выводились
    @JsonIgnore
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

    //игнорируем платформу, иначе рекурсия
    @JsonIgnore
    public Platforms getPlatform() {
        return platforms;
    }

    public void setPlatform(Platforms platform) {
        this.platforms = platform;
    }

    @Override
    public String toString() {
        return "ImagesT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", extension='" + extension + '\'' +
                ", data='" + (data.length() > 0 ? data.substring(0, 20) : data) + '\'' +
                '}';
    }
}
