package com.psu.kurs.demo.entity;

public class Images {
    private Long id;
    private String name;
    private String data;
    private String contentType;
    private String extension;

    public Images() {
    }

    public Images(Long id, String name, String data, String contentType, String extension) {
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



}
