package com.psu.kurs.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "girls", schema = "cursovaya", catalog = "kurss")
public class Girls extends AbstractEntity {
    private String nameGirl;

    public Girls() {
    }

    @ManyToOne (optional=false, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn (name="girls_id")
    private Man man;

    public String getNameGirl() {
        return nameGirl;
    }

    public void setNameGirl(String nameGirl) {
        this.nameGirl = nameGirl;
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public Man getMan() {
        return  man;
    }

    @Override
    public String toString() {
        return "Girls{" +
                "id='" + super.getId() + '\'' +
                "nameGirl='" + nameGirl + '\'' +
                '}';
    }
}
