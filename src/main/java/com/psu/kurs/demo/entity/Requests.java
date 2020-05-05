package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "requests", schema = "cursovaya", catalog = "kurss")
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_order_id")
    private FinalOrder finalOrder;

    private String date;

    private int numberOfDays;
    private double price;

    public Requests() {
    }

    public Requests(Products products, Basket basket, String date, int numberOfDays, double price) {
        this.products = products;
        this.basket = basket;
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

    @JsonBackReference
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public FinalOrder getFinalOrder() {
        return finalOrder;
    }

    public void setFinalOrder(FinalOrder finalOrder) {
        this.finalOrder = finalOrder;
    }

    @Override
    public String toString() {
        return "Requests{" +
                "id=" + id +
                ", products=" + products +
                ", IIIidBucket=" + basket +
                ", date='" + date + '\'' +
                ", numberOfDays=" + numberOfDays +
                ", price=" + price +
                '}';
    }
}
