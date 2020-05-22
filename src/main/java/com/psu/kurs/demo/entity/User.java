package com.psu.kurs.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "cursovaya", catalog = "kurss")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class User extends AbstractEntity {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            schema = "cursovaya",
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

    private Double order_amount;

    @Column
    private Double discount_rate;

    @Getter
    @Setter
    @Column(name = "balance", columnDefinition = "double precision default 0")
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<FinalOrder> finalOrderList;

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

    public Double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Double order_amount) {
        this.order_amount = order_amount;
    }

    public Double getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Double discount_rate) {
        this.discount_rate = discount_rate;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
