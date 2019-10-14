package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeLimitsRepository extends JpaRepository<AgeLimits,Long> {
}
