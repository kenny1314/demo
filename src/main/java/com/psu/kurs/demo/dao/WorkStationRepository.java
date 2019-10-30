package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStationRepository extends JpaRepository<WorkStation,Long> {
}
