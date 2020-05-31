package com.psu.kurs.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class DecoupledLogicSetup {
//    @Autowired
//    private SpringResourceTemplateResolver templateResolver;
//
//    @PostConstruct
//    public void init() {
//        templateResolver.setUseDecoupledLogic(true);
//        System.out.println("Decoupled template logic enabled");
//    }
}
