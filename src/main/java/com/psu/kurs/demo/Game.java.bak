package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "age_limits_id")
    private AgeLimits ageLimits;


    public Game() {
    }

    public Game(Long id, String name, String description, AgeLimits age_limits) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.ageLimits = age_limits;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AgeLimits getAgeLimits() {
        return ageLimits;
    }

    public void setAgeLimits(AgeLimits ageLimits) {
        this.ageLimits = ageLimits;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ageLimits=" + ageLimits +
                '}';
    }
}
