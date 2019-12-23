package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "girls", schema = "cursovaya", catalog = "kurss")
public class Girls {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @JsonBackReference
    public Man getMan() {
        return  man;
    }

//    @Override
//    public String toString() {
//        return "Girls{" +
//                "id=" + id +
//                ", nameGirl='" + nameGirl + '\'' +
//                ", man=" + man +
//                '}';
//    }
}
