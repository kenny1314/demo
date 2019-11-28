package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "type_of_delivery", schema = "cursovaya", catalog = "kurss")
public class TypeOfDelivery {
    @Id
    @Column(name = "id")
    private Long id;
    private String type;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "type_of_delivery_id")
    private List<Delivery> deliveryList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    @Override
    public String toString() {
        return "TypeOfDelivery{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", deliveryList=" + deliveryList +
                '}';
    }
}
