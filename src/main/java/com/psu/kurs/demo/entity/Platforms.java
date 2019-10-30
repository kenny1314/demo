package com.psu.kurs.demo.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "platforms", schema = "cursovaya", catalog = "kurss")
public class Platforms {
    private Long id;
    private String name;
    private String manufacturer;
    private String generation;
    private String relaseDate;
    private String piecesSold;
    private String cpu;
    private String description;
    private String story;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "emp1_workstation", joinColumns = {@JoinColumn(name = "platforms_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "imagest_id", referencedColumnName = "id")})
//    private ImagesT imagesT;

    private List<Products> productsList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "generation")
    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    @Basic
    @Column(name = "relase_date")
    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }

    @Basic
    @Column(name = "pieces_sold")
    public String getPiecesSold() {
        return piecesSold;
    }

    public void setPiecesSold(String piecesSold) {
        this.piecesSold = piecesSold;
    }

    @Basic
    @Column(name = "cpu")
    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Basic
    @Type(type = "text")
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Type(type = "text")
    @Column(name = "story")
    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "platforms_id")
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

//    public ImagesT getImagesT() {
//        return imagesT;
//    }
//
//    public void setImagesT(ImagesT imagesT) {
//        this.imagesT = imagesT;
//    }

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
//                ", imagesT=" + imagesT.toString() +
                '}';
    }
}
