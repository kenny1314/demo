package com.psu.kurs.demo.config.service;


import com.psu.kurs.demo.config.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
