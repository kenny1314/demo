package com.psu.kurs.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "languages", schema = "cursovaya", catalog = "kurss")
public class Languages {
    private Long id;
    private String name;
    private List<Products> productsList;

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

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonBackReference //избежать печать всех продуктов
    @JoinColumn(name = "languages_id")
    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
