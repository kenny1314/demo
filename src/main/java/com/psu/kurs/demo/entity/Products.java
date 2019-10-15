package com.psu.kurs.demo.entity;

import javax.persistence.*;

//5 доп таблиц

@Entity
@Table(name = "products", schema = "cursovaya", catalog = "kurss")
public class Products {
    private int id;
    private String title;
    private String description;
    private int YearOfIssue;
    private Double oneDayPrice;
    private Double fullPrice;
    private Integer quantity;

    private AgeLimits ageLimits;
    private Genres genres;
    private Languages languages;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "year_of_issue")
    public int getYearOfIssue() {
        return YearOfIssue;
    }

    public void setYearOfIssue(int YearOfIssue) {
        this.YearOfIssue = YearOfIssue;
    }

    @Basic
    @Column(name = "one_day_price")
    public Double getOneDayPrice() {
        return oneDayPrice;
    }

    public void setOneDayPrice(Double oneDayPrice) {
        this.oneDayPrice = oneDayPrice;
    }

    @Basic
    @Column(name = "full_price")
    public Double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Double fullPrice) {
        this.fullPrice = fullPrice;
    }

    @Basic
    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_limits_id")
    public AgeLimits getAgeLimits() {
        return ageLimits;
    }

    public void setAgeLimits(AgeLimits ageLimits) {
        this.ageLimits = ageLimits;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genres_id")
    public Genres getGenres() {
        return genres;
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "languages_id")
    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }
}
