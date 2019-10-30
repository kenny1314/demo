package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.ImagesT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesTRepository extends JpaRepository<ImagesT,Long> {
}
