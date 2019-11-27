package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Delivery;
import com.psu.kurs.demo.entity.TypeOfDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfDeliveryRepository extends JpaRepository<TypeOfDelivery, Long> {
}
