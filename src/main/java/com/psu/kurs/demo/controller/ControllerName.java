package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.entity.Game;
import com.psu.kurs.demo.entity.Student;
import com.psu.kurs.demo.form.GameServices;
import com.psu.kurs.demo.form.StudentServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ControllerName {

    private static Logger logger = LoggerFactory.getLogger(ControllerName.class);

    @Autowired
    StudentServices studentServices;

    @Autowired
    GameServices gameServices;

    @GetMapping("/")
    public String index(Model model) {

//        studentServices.addGameDef();
//        gameServices.addGameDef();

        List<Student> listStudent = studentServices.list();
        logger.info("list: " + listStudent.size());

        List<Game> listGame = gameServices.list();
        logger.info("list game: " + listGame.size());


        model.addAttribute("listGame", listGame);

        model.addAttribute("listStudent", listStudent);
        return "index";
    }

    @GetMapping("/students")
    public String studentsList(Model model) {

        List<Student> listStudent = studentServices.list();
        logger.info("list: " + "fsfffffffffffffffffff");
        model.addAttribute("listStudent", listStudent);
        return "accountsPage";
    }


    @GetMapping("/page")
    public String page(Model model) {
        return "page2";
    }


}
