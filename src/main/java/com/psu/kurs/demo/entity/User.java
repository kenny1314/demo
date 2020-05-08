package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "cursovaya", catalog = "kurss")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class User extends AbstractEntity {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;
    @Column(name = "enabled")
    private int enabled;
    @Column
    private String username;
    @Column
    private String password;

    private double count_reqests;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<FinalOrder> finalOrderList;

    //TODO для адреса
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id", referencedColumnName = "id")
//    private Address address;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    public User() {
    }

    public User(int enabled, String username, String password, List<Role> roles) {
        this.enabled = enabled;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getCount_reqests() {
        return count_reqests;
    }

    public void setCount_reqests(double count_reqests) {
        this.count_reqests = count_reqests;
    }

    public List<FinalOrder> getFinalOrderList() {
        return finalOrderList;
    }

    public void setFinalOrderList(List<FinalOrder> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
