package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.AddressRepository;
import com.psu.kurs.demo.dao.RoleRepository;
import com.psu.kurs.demo.dao.UserRepository;
import com.psu.kurs.demo.entity.Address;
import com.psu.kurs.demo.entity.User;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class LogRegisController {

    private static Logger logger = LoggerFactory.getLogger(LogRegisController.class);
    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/login")
    public String login(Model model) {
        model = menuService.getMenuItems(model); //get menu items
        return "login";
    }

    @GetMapping("/registration")
    public String reg(Model model) {
        model = menuService.getMenuItems(model); //get menu items

        return "/registration";
    }


//    @GetMapping("/delusr")
//    public @ResponseBody
//    String delUsr() {
////        userService.deleteUserWithRole(4L);
//        return "test del usr";
//    }

    //что-то с транзакциями
    @PersistenceContext
    private EntityManager entityManager;

    //обработка регистрации
    @PostMapping("/registration")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("city") String city,
                          @RequestParam("street") String street,
                          @RequestParam("flatNumber") String flatNumber,
                          Model model) {
        logger.info("post reg");

        model = menuService.getMenuItems(model); //get menu items

        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Пользователь " + username + " уже зарегистрирован");
            return "/registration";
        }
        if (!(username.matches("^[a-zA-Z0-9]+$"))) {
            model.addAttribute("error", "Имя пользователя может содержать только латиницу и цифры");
            return "/registration";
        }
        User user = new User(1, username, password, Arrays.asList(roleRepository.findByName("ROLE_USER")));

        Long addressID = null;
        if (addressRepository.findAll().size() > 0) {
            List<Long> addressList = new ArrayList<>();

            for (Address addres : addressRepository.findAll()) {
                addressList.add(addres.getId());
            }
            addressID = Collections.max(addressList);
            addressID++;
            System.out.println("addressiD: "+addressID);
        }

        Address address = new Address();
        address.setId(addressID);
        address.setCity(city);
        address.setStreet(street);
        address.setFlatNumber(flatNumber);
        addressRepository.save(address);

        user.setAddress(address);
        userService.save(user);

        model.addAttribute("error", "Всё хорошо");
        return "redirect:/";
    }
}
