package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeLimitsRepository extends JpaRepository<AgeLimits,Long> {
}
