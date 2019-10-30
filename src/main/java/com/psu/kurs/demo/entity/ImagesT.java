package com.psu.kurs.demo.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "imagest", schema = "cursovaya", catalog = "kurss")
public class ImagesT {
    private Long id;
    private String name;
    private String data;
    private String contentType;
    private String extension;

//    @OneToOne(mappedBy = "imagest")
//    private Platforms platform;

    public ImagesT() {
    }

    public ImagesT(Long id, String name, String data, String contentType, String extension) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.contentType = contentType;
        this.extension = extension;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Basic
    @Type(type = "text")
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "content_type")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Basic
    @Column(name = "extension")
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

//    public Platforms getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(Platforms platform) {
//        this.platform = platform;
//    }

    @Override
    public String toString() {
        return "ImagesT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", extension='" + extension + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
