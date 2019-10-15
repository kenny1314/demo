package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
}
