package com.psu.kurs.demo.config.repository;

import com.psu.kurs.demo.config.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
