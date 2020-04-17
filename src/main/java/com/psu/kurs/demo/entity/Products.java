package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

//5 доп таблиц

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "products", schema = "cursovaya", catalog = "kurss")
public class Products {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "idd")
    private Long idd;
    private String title;

    @Type(type = "text")
    private String description;
    private int yearOfIssue;
    private Double oneDayPrice;
    private Double fullPrice;
    private Integer quantity;

    public Long getIdd() {
        return idd;
    }

    public void setIdd(Long idd) {
        this.idd = idd;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_limits_id")
    private AgeLimits ageLimits;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "genres_id")
    private Genres genres;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languages_id")
    private Languages languages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platforms_id")
    private Platforms platforms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishers_id")
    private Publishers publishers;

    @OneToOne(cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JoinColumn(name = "imagesp_id", referencedColumnName = "id")
    private ImagesP imagesP;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)

//    @OneToMany (mappedBy="man", fetch=FetchType.LAZY)
//    @OneToMany
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @JoinColumn(name = "products_id")
//    private List<Requests> requestsList;


    public Products() {
    }

    public Products(Long id, String title, String description, int yearOfIssue, Double oneDayPrice, Double fullPrice,
                    Integer quantity, AgeLimits ageLimits, Genres genres, Languages languages, Platforms platforms, Publishers publishers) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.yearOfIssue = yearOfIssue;
        this.oneDayPrice = oneDayPrice;
        this.fullPrice = fullPrice;
        this.quantity = quantity;
        this.ageLimits = ageLimits;
        this.genres = genres;
        this.languages = languages;
        this.platforms = platforms;
        this.publishers = publishers;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Double getOneDayPrice() {
        return oneDayPrice;
    }

    public void setOneDayPrice(Double oneDayPrice) {
        this.oneDayPrice = oneDayPrice;
    }

    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public AgeLimits getAgeLimits() {
        return ageLimits;
    }

    public void setAgeLimits(AgeLimits ageLimits) {
        this.ageLimits = ageLimits;
    }

    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    public Platforms getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Platforms platforms) {
        this.platforms = platforms;
    }

    public Publishers getPublishers() {
        return publishers;
    }

    public void setPublishers(Publishers publishers) {
        this.publishers = publishers;
    }

    public ImagesP getImagesP() {
        return imagesP;
    }

    public void setImagesP(ImagesP imagesP) {
        this.imagesP = imagesP;
    }


//    @JsonManagedReference
//    public List<Requests> getRequestsList() {
//        return requestsList;
//    }

//    public void setRequestsList(List<Requests> requestsList) {
//        this.requestsList = requestsList;
//    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", yearOfIssue=" + yearOfIssue +
                ", oneDayPrice=" + oneDayPrice +
                ", fullPrice=" + fullPrice +
                ", quantity=" + quantity +
                ", ageLimits=" + ageLimits.getYear() +
                ", genres=" + genres.getName() +
                ", languages=" + languages.getName() +
                ", platforms=" + platforms.getName() +
                ", publishers=" + publishers.getName() +
//                ", requests=" + requests.getId() +
                '}';
    }


}
