package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<Requests,Long> {
}
