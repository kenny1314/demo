package com.psu.kurs.demo.entity.After;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Clents {
    private int id;
    private String login;
    private String password;
    private Date dateC;
    private Integer countRequests;
    private String name;
    private String surname;
    private Roles roles;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "date_c")
    public Date getDateC() {
        return dateC;
    }

    public void setDateC(Date dateC) {
        this.dateC = dateC;
    }

    @Basic
    @Column(name = "count_requests")
    public Integer getCountRequests() {
        return countRequests;
    }

    public void setCountRequests(Integer countRequests) {
        this.countRequests = countRequests;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    @Override
    public String toString() {
        return "Clents{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", dateC=" + dateC +
                ", countRequests=" + countRequests +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}