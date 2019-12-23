package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "man", schema = "cursovaya", catalog = "kurss")
public class Man extends AbstractEntity {
    private String nameMan;



    @OneToMany (mappedBy="man", fetch=FetchType.LAZY)
    private List<Girls> girlsList;

    public Man() {
    }

    public String getNameMan() {
        return nameMan;
    }

    public void setNameMan(String nameMan) {
        this.nameMan = nameMan;
    }

    public List<Girls> getGirlsList() {
        return girlsList;
    }

    public void setGirlsList(List<Girls> girlsList) {
        this.girlsList = girlsList;
    }

    @Override
    public String toString() {
        return "Man{" +
                "id='" + super.getId() + '\'' +
                "nameMan='" + nameMan + '\'' +
                '}';
    }
}
