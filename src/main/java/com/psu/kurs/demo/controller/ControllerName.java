package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.entity.Game;
import com.psu.kurs.demo.form.GameServices;
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
    GameServices gameServices;

    @GetMapping("/")
    public String index(Model model) {

//        gameServices.addGameDef();

        List<Game> listGame = gameServices.list();
        logger.info("list game: " + listGame.size());


        model.addAttribute("listGame", listGame);

        return "index";
    }

    @GetMapping("/game")
    public String page(Model model) {
        return "game";
    }


}
