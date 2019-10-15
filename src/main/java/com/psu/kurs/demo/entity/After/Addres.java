package com.psu.kurs.demo.entity.After;

import javax.persistence.*;

@Entity
//@Table(name = "addres", schema = "cursovaya", catalog = "kurss")
public class Addres {
    private int id;
    private String street;
    private String home;
    private Integer flatNumber;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "addres_id")
//    private List<Delivery> deliveryMList = new ArrayList<>();


    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "home")
    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    @Basic
    @Column(name = "flat_number")
    public Integer getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Addres addres = (Addres) o;

        if (id != addres.id) return false;
        if (street != null ? !street.equals(addres.street) : addres.street != null) return false;
        if (home != null ? !home.equals(addres.home) : addres.home != null) return false;
        if (flatNumber != null ? !flatNumber.equals(addres.flatNumber) : addres.flatNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (home != null ? home.hashCode() : 0);
        result = 31 * result + (flatNumber != null ? flatNumber.hashCode() : 0);
        return result;
    }

//    public List<Delivery> getDeliveryMList() {
//        return deliveryMList;
//    }
//
//    public void setDeliveryMList(List<Delivery> deliveryMList) {
//        this.deliveryMList = deliveryMList;
//    }

    @Override
    public String toString() {
        return "Addres{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", home='" + home + '\'' +
                ", flatNumber=" + flatNumber +
//                ", addresList=" + addresList +
                '}';
    }
}
