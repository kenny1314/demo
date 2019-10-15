package com.psu.kurs.demo.entity.After;

import javax.persistence.*;
import java.sql.Date;

@Entity
//@Table(name = "final_order", schema = "cursovaya", catalog = "kurss")
public class FinalOrder {
    private int id;
    private Date date;
    private Integer finalPrice;

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
    @Column(name = "final_price")
    public Integer getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Integer finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalOrder that = (FinalOrder) o;

        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (finalPrice != null ? !finalPrice.equals(that.finalPrice) : that.finalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (finalPrice != null ? finalPrice.hashCode() : 0);
        return result;
    }
}
