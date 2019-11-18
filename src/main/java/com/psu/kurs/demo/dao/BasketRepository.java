package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket,Long> {
}
