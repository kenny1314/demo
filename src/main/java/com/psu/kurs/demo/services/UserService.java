package com.psu.kurs.demo.services;


import com.psu.kurs.demo.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();

    User findByUsername(String username);

    void deleteUserWithRole(Long id);
}