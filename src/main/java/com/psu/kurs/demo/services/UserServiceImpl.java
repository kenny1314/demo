package com.psu.kurs.demo.services;

import com.psu.kurs.demo.dao.AddressRepository;
import com.psu.kurs.demo.dao.RoleRepository;
import com.psu.kurs.demo.dao.UserRepository;
import com.psu.kurs.demo.entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        }
        userRepository.saveAndFlush(user);
    }

    @PersistenceContext
    private EntityManager entityManager;

    //TODO не работает, нужно менять id адресса
    @Transactional
    @Override
    public void deleteUserWithRole(Long id) {

        Session session = null;
        if (entityManager == null
                || (session = entityManager.unwrap(Session.class)) == null) {

            throw new NullPointerException();
        }
        session.createSQLQuery("delete from user_roles as ur where ur.user_id=:id")
                .setInteger("id", id.intValue());
        User user = userRepository.getOne(id);
        userRepository.deleteById(id);
        System.out.println("addr: sise:"+addressRepository.findAll().size());
//        addressRepository.deleteById(user.getAddress().getId());

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}