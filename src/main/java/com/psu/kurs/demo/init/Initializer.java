package com.psu.kurs.demo.init;

import com.psu.kurs.demo.dao.RoleRepository;
import com.psu.kurs.demo.entity.Role;
import com.psu.kurs.demo.entity.User;
import com.psu.kurs.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

//@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Initializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserService userService;


    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (roleRepository.findAll().size() == 0) {
            System.out.println("\n______________INITIALIZATION______________");

            //roles
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.saveAndFlush(role);
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.saveAndFlush(role);

            Role role0 = new Role();
            role0.setName("ROLE_COURIER");
            roleRepository.saveAndFlush(role0);


            //users
            User user = new User();
            user.setUsername("admin");
            user.setPassword("1234");
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
            userService.save(user);
            user = new User();
            user.setUsername("user");
            user.setPassword("1234");
            userService.save(user);

            User user0 = new User();
            user0.setUsername("courier");
            user0.setPassword("1234");
            user0.setRoles(Arrays.asList(roleRepository.findByName("ROLE_COURIER")));
            userService.save(user0);



        }
    }

}
