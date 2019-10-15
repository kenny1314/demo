package com.psu.kurs.demo.entity.After;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "cursovaya", catalog = "kurss")
public class Users {
    private Long id;
    private String name;
    private Ro ro;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ro_id")
    public Ro getRo() {
        return ro;
    }

    public void setRo(Ro ro) {
        this.ro = ro;
    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roGender=" + ro.getGender() +
                '}';
    }
}
