package com.psu.kurs.demo.form;

import com.psu.kurs.demo.dao.GameRepository;
import com.psu.kurs.demo.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServices {

    @Autowired
    GameRepository gameRepository;

    public List<Game> list(){
        return gameRepository.findAll();
    }

    public void addGameDef(){
//        gameRepository.save(new Game(100L,"BattleT99","I'm form Germany",0L));
    }
}
