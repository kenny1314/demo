package com.psu.kurs.demo.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "final_order", schema = "cursovaya", catalog = "kurss")
public class FinalOrder {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "final_order_id")
    private List<Requests> requestsList;

    //TODO для пользователя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private double finalPrice;

    @Column(name = "total_price", columnDefinition = "double precision null default 0.0")
    private double totalPrice;

    private Date date;

    @Column(name = "is_delivered", columnDefinition = "boolean default false")
    private boolean idDelivered;

    @Column(name = "is_keep_yourself", columnDefinition = "boolean default false")
    private boolean keepYourself;

    @Column(name = "is_completed", columnDefinition = "boolean default false")
    private boolean completed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
    private Delivery delivery;

    public FinalOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isIdDelivered() {
        return idDelivered;
    }

    public void setIdDelivered(boolean idDelivered) {
        this.idDelivered = idDelivered;
    }

    public boolean isKeepYourself() {
        return keepYourself;
    }

    public void setKeepYourself(boolean keepYourself) {
        this.keepYourself = keepYourself;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        return "FinalOrder{" +
                "id=" + id +
//                ", requestsList=" + requestsList +
                ", finalPrice=" + finalPrice +
                ", date=" + date +
                '}';
    }
}
