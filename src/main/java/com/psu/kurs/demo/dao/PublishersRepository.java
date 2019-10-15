package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.entity.Publishers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishersRepository extends JpaRepository<Publishers,Long> {
}
