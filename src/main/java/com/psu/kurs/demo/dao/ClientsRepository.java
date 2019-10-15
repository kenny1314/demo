package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.After.Clents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clents,Long> {
}
