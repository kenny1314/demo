package com.psu.kurs.demo.dao.dep;

import com.psu.kurs.demo.entity.AddressD;
import com.psu.kurs.demo.entity.Man;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManRepository extends JpaRepository<Man, Long> {
}
