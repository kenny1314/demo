package com.psu.kurs.demo.entity.After;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Delivery {

    private int id;
    private Date dateOfDelivery;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "addres_id")
//    private Addres addres;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date_of_delivery")
    public Date getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(Date dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (id != delivery.id) return false;
        if (dateOfDelivery != null ? !dateOfDelivery.equals(delivery.dateOfDelivery) : delivery.dateOfDelivery != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateOfDelivery != null ? dateOfDelivery.hashCode() : 0);
        return result;
    }

//    public Addres getAddres() {
//        return addres;
//    }
//
//    public void setAddres(Addres addres) {
//        this.addres = addres;
//    }
}
