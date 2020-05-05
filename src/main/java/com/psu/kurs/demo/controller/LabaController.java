package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.services.PostgreSqlExample;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

@Controller
public class LabaController {

    PostgreSqlExample tableRepository = PostgreSqlExample.getInstance();

    @GetMapping("/laba")
    public String add(Model model) throws SQLException {
        model.addAttribute("state", 1);
        model.addAttribute("datnames", tableRepository.getDB());
        //model.addAttribute("tbls",tableRepository.getTables());
        return "laba";
    }

    @PostMapping("/laba")
    public String qweee(@RequestParam(value = "datname", required = false) String datname,
                        @RequestParam(value = "table", required = false) String tablename,
                        Model model) throws SQLException {
        if (datname != null) {

            tableRepository.reconnect(datname);
        }
        model.addAttribute("state", 2);
        model.addAttribute("cols", tableRepository.describeTabe(tablename));
        //model.addAttribute("datnames",tableRepository.getDB());
        model.addAttribute("tbls", tableRepository.getTables());
        return "laba";
    }
}
