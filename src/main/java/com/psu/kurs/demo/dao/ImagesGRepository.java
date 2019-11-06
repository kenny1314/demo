package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.ImagesG;
import com.psu.kurs.demo.entity.ImagesT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesGRepository extends JpaRepository<ImagesG,Long> {
}
