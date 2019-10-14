package com.psu.kurs.demo.form;

import com.psu.kurs.demo.dao.AgeLimitsRepository;
import com.psu.kurs.demo.entity.AgeLimits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeLimitsServices {

    @Autowired
    AgeLimitsRepository ageLimitsRepository;

    public List<AgeLimits> list(){
        return ageLimitsRepository.findAll();
    }

    public void addAgeLimitsDef(){
        ageLimitsRepository.save(new AgeLimits(1L,1999));
    }
}
