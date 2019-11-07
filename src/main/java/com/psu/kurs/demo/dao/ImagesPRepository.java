package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.ImagesG;
import com.psu.kurs.demo.entity.ImagesP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesPRepository extends JpaRepository<ImagesP,Long>  {
}
