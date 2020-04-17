package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "man", schema = "cursovaya", catalog = "kurss")
public class Man extends AbstractEntity {
    private String nameMan;

    @OneToMany(mappedBy = "man", fetch = FetchType.LAZY)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Girls> girlsList;

    public Man() {
    }

    public String getNameMan() {
        return nameMan;
    }

    public void setNameMan(String nameMan) {
        this.nameMan = nameMan;
    }

    @JsonManagedReference
    public List<Girls> getGirlsList() {
        return girlsList;
    }

    public void setGirlsList(List<Girls> girlsList) {
        this.girlsList = girlsList;
    }

//    @Override
//    public String toString() {
//        return "Man{" +
//                "id='" + super.getId() + '\'' +
//                "nameMan='" + nameMan + '\'' +
//                '}';
//    }
}
