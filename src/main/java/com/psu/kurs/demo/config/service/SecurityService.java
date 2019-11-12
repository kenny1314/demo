package com.psu.kurs.demo.config.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
