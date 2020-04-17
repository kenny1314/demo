package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "genres", schema = "cursovaya", catalog = "kurss")
public class Genres {
    @Id
    @Column(name = "id")
    private Long id;
    private String name;
//мб тут
    @OneToMany(targetEntity = Products.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idd")
    private List<Products> productsList;

    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "imagesg_id", referencedColumnName = "id")
    private ImagesG imagesG;

    public Genres() {
    }

    public Genres(Long id, String name, ImagesG imagesG) {
        this.id = id;
        this.name = name;
        this.imagesG = imagesG;
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


    @JsonIgnore
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public ImagesG getImagesG() {
        return imagesG;
    }

    public void setImagesG(ImagesG imagesG) {
        this.imagesG = imagesG;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productsList=" + productsList +
                ", img= "+imagesG.toString()+
                '}';
    }
}
