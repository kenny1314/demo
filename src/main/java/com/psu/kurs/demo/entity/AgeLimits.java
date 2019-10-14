package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "age_limits")
public class AgeLimits {
    @Id
    @GeneratedValue
    private Long id;
    private int year;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "age_limits_id")
    private List<Game> gameList=new ArrayList<>();

    public AgeLimits() {
    }

    public AgeLimits(Long id, int year) {
        super();
        this.id = id;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    @Override
    public String toString() {
        return "AgeLimits{" +
                "id=" + id +
                ", year=" + year +
                ", gameList=" + gameList +
                '}';
    }
}
