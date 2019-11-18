package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "basket", schema = "cursovaya", catalog = "kurss")
public class Basket {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id")
    private List<Requests> requestsList;

    private double finalPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
//                ", requestsList=" + requestsList +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
