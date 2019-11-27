package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Address;
import com.psu.kurs.demo.entity.AddressD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDRepository extends JpaRepository<AddressD, Long> {
}
