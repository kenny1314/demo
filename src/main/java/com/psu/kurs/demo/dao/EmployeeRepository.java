package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Employee;
import com.psu.kurs.demo.entity.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
