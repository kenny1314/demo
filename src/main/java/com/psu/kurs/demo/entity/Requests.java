package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "requests", schema = "cursovaya", catalog = "kurss")
public class Requests {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Products products;

    private Long idBucket;

    private String date;

    private int numberOfDays;
    private double price;

    public Requests() {
    }

    public Requests(Long id, Products products, Long idBucket, String date, int numberOfDays, double price) {
        this.id = id;
        this.products = products;
        this.idBucket = idBucket;
        this.date = date;
        this.numberOfDays = numberOfDays;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public Long getIdBucket() {
        return idBucket;
    }

    public void setIdBucket(Long idBucket) {
        this.idBucket = idBucket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Requests{" +
                "id=" + id +
                ", products=" + products +
                ", idBucket=" + idBucket +
                ", date='" + date + '\'' +
                ", numberOfDays=" + numberOfDays +
                ", price=" + price +
                '}';
    }
}
