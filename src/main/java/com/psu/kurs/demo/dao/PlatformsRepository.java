package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformsRepository extends JpaRepository<Platforms,Long> {
}
