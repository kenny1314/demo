package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenresRepository extends JpaRepository<Genres,Long> {
}
