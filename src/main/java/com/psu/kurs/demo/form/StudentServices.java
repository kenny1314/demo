package com.psu.kurs.demo.form;

import com.psu.kurs.demo.dao.StudentRepository;
import com.psu.kurs.demo.entity.Game;
import com.psu.kurs.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServices {

    @Autowired
    StudentRepository studentRepository;

    public List<Student> list(){
        return studentRepository.findAll();
    }

    public void addGameDef(){
        studentRepository.save(new Student(100L,"Igor","passmy"));
    }
}
