package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "address", schema = "cursovaya", catalog = "kurss")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String city;
    private String street;
    private String flatNumber;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private List<User> userList;


    public Address() {
    }

    public Address(Long id, String city, String street, String flatNumber) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", flatNumber='" + flatNumber + '\'' +
                '}';
    }
}
