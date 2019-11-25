package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.FinalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalOrderRepository extends JpaRepository<FinalOrder, Long> {
}
