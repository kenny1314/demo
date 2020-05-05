package com.psu.kurs.demo.dao.dep;

import com.psu.kurs.demo.entity.Girls;
import com.psu.kurs.demo.entity.Man;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GirlsRepository extends JpaRepository<Girls, Long> {
}
