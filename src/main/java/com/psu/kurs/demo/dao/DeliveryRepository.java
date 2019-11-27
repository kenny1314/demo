package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Address;
import com.psu.kurs.demo.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
