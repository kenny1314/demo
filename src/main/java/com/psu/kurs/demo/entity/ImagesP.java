package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "imagesp", schema = "cursovaya", catalog = "kurss")
public class ImagesP extends Images {

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
    @OneToOne(mappedBy = "imagesP")
    private Products products;

    public ImagesP() {
        super();
    }

    public ImagesP(Long id, String name, String data, String contentType, String extension) {
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

    //игнорируем продукт, иначе рекурсия
    @JsonIgnore
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ImagesP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", extension='" + extension + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
