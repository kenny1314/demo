package com.psu.kurs.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.math.BigInteger;


@AllArgsConstructor
@NoArgsConstructor
public class CityNameDTO {
    @Getter
    @Setter
    public BigInteger id;

    @Getter
    @Setter
    public String city;

    @Override
    public String toString() {
        return "CityNameDTO{" +
                "id='" + id + '\'' +
                "city='" + city + '\'' +
                '}';
    }
}
