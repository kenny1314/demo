package com.psu.kurs.demo.form;

import com.psu.kurs.demo.dao.BankAccountRepository;
import com.psu.kurs.demo.dao.GameRepository;
import com.psu.kurs.demo.entity.BankAccount;
import com.psu.kurs.demo.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServices {

    @Autowired
    BankAccountRepository bankAccountRepository;

    public List<BankAccount> list(){
        return bankAccountRepository.findAll();
    }

    public void addGameDef(){
//        gameRepository.save(new Game(100L,"BattleT99","I'm form Germany",0L));
    }
}
