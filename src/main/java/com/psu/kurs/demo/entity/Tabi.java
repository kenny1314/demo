package com.psu.kurs.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "tabi", schema = "cursovaya", catalog = "kurss")
public class Tabi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Tabi() {
    }

    public Tabi(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
