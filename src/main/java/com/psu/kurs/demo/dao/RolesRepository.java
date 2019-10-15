package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.After.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {
}
