package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "platforms", schema = "cursovaya", catalog = "kurss")
public class Platforms {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private String manufacturer;
    private String generation;
    private String relaseDate;
    private String piecesSold;
    private String cpu;

    @Type(type = "text")
    private String description;

    @Type(type = "text")
    private String story;

    @OneToMany(targetEntity = Products.class)
    @JoinColumn(name = "idd")
    private List<Products> productsList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imagest_id", referencedColumnName = "id")
    private ImagesT imagesT;


    public Platforms() {
    }

    public Platforms(Long id, String name, String manufacturer, String generation, String relaseDate, String piecesSold, String cpu, String description, String story, ImagesT imagesT) {
        this.id=id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.generation = generation;
        this.relaseDate = relaseDate;
        this.piecesSold = piecesSold;
        this.cpu = cpu;
        this.description = description;
        this.story = story;
        this.imagesT=imagesT;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }

    public String getPiecesSold() {
        return piecesSold;
    }

    public void setPiecesSold(String piecesSold) {
        this.piecesSold = piecesSold;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @JsonIgnore
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public ImagesT getImagesT() {
        return imagesT;
    }

    public void setImagesT(ImagesT imagesT) {
        this.imagesT = imagesT;
    }

    @Override
    public String toString() {
        return "Platforms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", generation='" + generation + '\'' +
                ", relaseDate='" + relaseDate + '\'' +
                ", piecesSold='" + piecesSold + '\'' +
                ", cpu='" + cpu + '\'' +
                ", description='" + description + '\'' +
                ", story='" + story + '\'' +
                ", productsList=" + productsList +
                ", imagesT=" + imagesT.toString() +
                '}';
    }
}
