package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Products;
//import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Products findByTitle(String name);

    List<Products> findByTitleContainsIgnoreCase(String letter);
}
