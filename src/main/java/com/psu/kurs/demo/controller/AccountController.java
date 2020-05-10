package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.AddressRepository;
import com.psu.kurs.demo.dao.FinalOrderRepository;
import com.psu.kurs.demo.entity.Address;
import com.psu.kurs.demo.entity.FinalOrder;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UserService userService;

    @Autowired
    FinalOrderRepository finalOrderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    OtherService otherService;

    //TODO исправить название
    @GetMapping("/accountAdmin")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String accountAdmin(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        model.addAttribute("listFinalOrder", finalOrderRepository.findAll());

        return "accountAdmin";
    }

    @GetMapping("/accountUser") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String accountUser(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> newListFinalOrder = new ArrayList<>();

        for (FinalOrder fin : finalOrderRepository.findAll()) {
            if (fin.getUser().getId() == userService.findByUsername(principal.getName()).getId()) {
                newListFinalOrder.add(fin);
            }
        }

        model.addAttribute("listFinalOrder", newListFinalOrder);

        return "accountUser";
    }

    //TODO информация об аккаунте изменить ссылку
    @GetMapping("/infoUser")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String infoUser(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

//        System.out.println(addressRepository.findAll().size());

        model.addAttribute("address", addressRepository.getOne(userService.findByUsername(principal.getName()).getAddress().getId()));
        model.addAttribute("usr", userService.findByUsername(principal.getName()));

        return "infoUser";
    }

    @PostMapping("/updateUserAddress")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String updateUserAddress(Model model, Principal principal, HttpServletRequest request,
                                    @ModelAttribute Address address) {

        Address address0 = userService.findByUsername(principal.getName()).getAddress();
        address0.setCity(address.getCity());
        address0.setStreet(address.getStreet());
        address0.setFlatNumber(address.getFlatNumber());

        addressRepository.save(address0);
        return "redirect:/infoUser";
    }

}
