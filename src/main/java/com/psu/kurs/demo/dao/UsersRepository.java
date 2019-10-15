package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.After.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
}
