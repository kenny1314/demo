package com.psu.kurs.demo.config.repository;

import com.psu.kurs.demo.config.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
