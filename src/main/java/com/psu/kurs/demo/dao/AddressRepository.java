package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
