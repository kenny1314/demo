package com.psu.kurs.demo.dao;

import com.psu.kurs.demo.entity.Genres;
import com.psu.kurs.demo.entity.Languages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository extends JpaRepository<Languages,Long> {
}
