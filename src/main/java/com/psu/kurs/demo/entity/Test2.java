package com.psu.kurs.demo.entity;


import javax.persistence.*;

@Entity
@Table(name = "test2", schema = "cursovaya", catalog = "kurss")
public class Test2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Test2() {
    }

    public Test2(Long id, String name) {
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


    @Override
    public String toString() {
        return "Test2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}