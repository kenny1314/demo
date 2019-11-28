package com.psu.kurs.demo.entity;

import com.psu.kurs.demo.CityNameDTO;

import javax.persistence.*;

//@org.hibernate.annotations.NamedNativeQueries({
//        @org.hibernate.annotations.NamedNativeQuery(
//                name = "findCityById",
//                query = "select id,city from cursovaya.addressd",
//                resultSetMapping = "city_name_dto"
//        )
//})
//
//@SqlResultSetMapping(
//        name = "city_name_dto",
//        classes = @ConstructorResult(
//                targetClass = CityNameDTO.class,
//                columns = {
//                        @ColumnResult(name = "id"),
//                        @ColumnResult(name = "city")
//                }
//        )
//)
@org.hibernate.annotations.NamedNativeQueries({
        @org.hibernate.annotations.NamedNativeQuery(
                name = "findCityById",
                query = "select id,city from cursovaya.addressd where id=:id",
                resultSetMapping = "city_name_dto"
        )
})

@SqlResultSetMapping(
        name = "city_name_dto",
        classes = @ConstructorResult(
                targetClass = CityNameDTO.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "city")
                }
        )
)
@Entity
@Table(name = "addressd", schema = "cursovaya", catalog = "kurss")
public class AddressD {
    @Id
    @Column(name = "id")
    private Long id;
    private String city;
    private String street;
    private String flatNumber;

    @OneToOne(mappedBy = "addressD")
    private Delivery delivery;

    public AddressD() {
    }

    public AddressD(Long id, String city, String street, String flatNumber) {
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
