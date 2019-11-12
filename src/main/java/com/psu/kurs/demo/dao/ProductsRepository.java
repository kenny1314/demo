package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}
