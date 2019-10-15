package com.psu.kurs.demo.entity.After;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Requests {
    private int id;
    private Date date;
    private Integer numberOfDays;
    private Double price;
    private Integer idFinalOrder;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "number_of_days")
    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "id_final_order")
    public Integer getIdFinalOrder() {
        return idFinalOrder;
    }

    public void setIdFinalOrder(Integer idFinalOrder) {
        this.idFinalOrder = idFinalOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requests requests = (Requests) o;

        if (id != requests.id) return false;
        if (date != null ? !date.equals(requests.date) : requests.date != null) return false;
        if (numberOfDays != null ? !numberOfDays.equals(requests.numberOfDays) : requests.numberOfDays != null)
            return false;
        if (price != null ? !price.equals(requests.price) : requests.price != null) return false;
        if (idFinalOrder != null ? !idFinalOrder.equals(requests.idFinalOrder) : requests.idFinalOrder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (numberOfDays != null ? numberOfDays.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (idFinalOrder != null ? idFinalOrder.hashCode() : 0);
        return result;
    }
}
