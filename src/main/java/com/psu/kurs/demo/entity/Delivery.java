package com.psu.kurs.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "delivery", schema = "cursovaya", catalog = "kurss")
public class Delivery {

    @Id
    @Column(name = "id")
    private Long id;


    private String date;

    @OneToOne(mappedBy = "delivery")
    private FinalOrder finalOrder;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressd_id", referencedColumnName = "id")
    private AddressD addressD;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_delivery_id")
    private TypeOfDelivery typeOfDelivery;

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

    public FinalOrder getFinalOrder() {
        return finalOrder;
    }

    public void setFinalOrder(FinalOrder finalOrder) {
        this.finalOrder = finalOrder;
    }

    public AddressD getAddressD() {
        return addressD;
    }

    public void setAddressD(AddressD addressD) {
        this.addressD = addressD;
    }

    public TypeOfDelivery getTypeOfDelivery() {
        return typeOfDelivery;
    }

    public void setTypeOfDelivery(TypeOfDelivery typeOfDelivery) {
        this.typeOfDelivery = typeOfDelivery;
    }
}
