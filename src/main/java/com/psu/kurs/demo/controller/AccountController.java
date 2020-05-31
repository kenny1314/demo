package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.AddressRepository;
import com.psu.kurs.demo.dao.FinalOrderRepository;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.dao.UserRepository;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.FinalOrderComparator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    ProductsRepository productsRepository;

    //TODO исправить название
    @GetMapping("/accountAdmin")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String accountAdmin(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> newListFinalOrder=finalOrderRepository.findAll();

        Collections.sort(newListFinalOrder, new FinalOrderComparator());

        model.addAttribute("listFinalOrder", newListFinalOrder);

        return "/account/accountAdmin";
    }

    @GetMapping("/accountUser") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountUser(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> newListFinalOrder = new ArrayList<>();

        //выбрать заказы по id  usera и заказы не завершены
        for (FinalOrder fin : finalOrderRepository.findAll()) {
            if ((fin.getUser().getId() == userService.findByUsername(principal.getName()).getId()) && !fin.isCompleted()) {
                newListFinalOrder.add(fin);
            }
        }

        Collections.sort(newListFinalOrder, new FinalOrderComparator());

        model.addAttribute("listFinalOrder", newListFinalOrder);

        return "/account/accountUser";
    }

    @GetMapping("/accountUserCompleted") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountUserComplite(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> newListFinalOrder = new ArrayList<>();

        //выбрать заказы по id  usera и заказы не завершены
        for (FinalOrder fin : finalOrderRepository.findAll()) {
            if ((fin.getUser().getId() == userService.findByUsername(principal.getName()).getId()) && fin.isCompleted()) {
                newListFinalOrder.add(fin);
            }
        }

        Collections.sort(newListFinalOrder, new FinalOrderComparator());

        model.addAttribute("listFinalOrder", newListFinalOrder);

        return "/account/accountUserCompleted";
    }

    //TODO информация об аккаунте изменить ссылку
    @GetMapping("/infoUser")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String infoUser(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

//        System.out.println(addressRepository.findAll().size());

        model.addAttribute("address", addressRepository.getOne(userService.findByUsername(principal.getName()).getAddress().getId()));
        model.addAttribute("usr", userService.findByUsername(principal.getName()));

        return "/account/infoUser";
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


    @GetMapping("/confirmOrders") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String confirmOrders(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();

        List<FinalOrder> finalOrderListNew = new ArrayList<>();
        System.out.println("sizefinalorder: " + finalOrderList.size());
        for (FinalOrder finalOrder : finalOrderList) {
            if (!finalOrder.isIdDelivered()) {
                finalOrderListNew.add(finalOrder);
            }
        }

        Collections.sort(finalOrderListNew, new FinalOrderComparator());

        model.addAttribute("listFinalOrder", finalOrderListNew);

        return "/account/confirmOrders";
    }

    @PostMapping("/confirmDeliveryCourier") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String confirmDeliveryCourier(Model model, Principal principal, HttpServletRequest request,
                                         @RequestParam("id") String id, RedirectAttributes redirectAttributes) {


        model = menuService.getMenuItems(model); //get menu items

        System.out.println("idd: " + id);

        FinalOrder finalOrder0 = finalOrderRepository.getOne(Long.valueOf(id));
        finalOrder0.setIdDelivered(true);
        finalOrderRepository.saveAndFlush(finalOrder0);

        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();

        List<FinalOrder> finalOrderListNew = new ArrayList<>();
        System.out.println("sizefinalorder: " + finalOrderList.size());
        for (FinalOrder finalOrder : finalOrderList) {
            if (!finalOrder.isIdDelivered()) {
                finalOrderListNew.add(finalOrder);
            }
        }

//        model.addAttribute("listFinalOrder", finalOrderListNew);

        redirectAttributes.addFlashAttribute("listFinalOrder", finalOrderListNew);

        return "redirect:/confirmOrders";
    }

    @GetMapping("/ordersWithStatus") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String ordersWithStatus(Model model, Principal principal, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();

        Collections.sort(finalOrderList, new FinalOrderComparator());

        model.addAttribute("listFinalOrder", finalOrderList);

        return "/account/ordersWithStatus";
    }

    @PostMapping("/returnOrder") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String returnOrder(Model model, Principal principal, HttpServletRequest request,
                              @RequestParam("id") String id, RedirectAttributes redirectAttributes) {

        User user = userService.findByUsername(principal.getName());
        model = menuService.getMenuItems(model); //get menu items

        System.out.println("idd: " + id);

        Double pricePerDay = null;

        FinalOrder finalOrder0 = finalOrderRepository.getOne(Long.valueOf(id));
        finalOrder0.setCompleted(true);
        Double finalPrice = finalOrder0.getFinalPrice();
        Double totalPrice = finalOrder0.getTotalPrice();

        finalOrderRepository.saveAndFlush(finalOrder0);

        System.out.println("balance before: " + user.getBalance());
        System.out.println("final prc: " + finalPrice);
        System.out.println("total prc: " + totalPrice);
        user.setBalance(user.getBalance() + totalPrice - finalPrice);
        System.out.println("balance after: " + user.getBalance());

        userRepository.save(user);

        List<Products> productsList0=new ArrayList<>();
       for(Requests requests: finalOrder0.getRequestsList()){
           productsList0.add(requests.getProducts());
       }


        List<Products> productsList = new ReduceGameService().increaseNumbOfGame(productsList0, productsRepository);
        productsRepository.saveAll(productsList);

        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();

        redirectAttributes.addFlashAttribute("listFinalOrder", finalOrderList);

        return "redirect:/accountUser";
    }


    @PostMapping("/keepYourself") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String keepYourself(Model model, Principal principal, HttpServletRequest request,
                               @RequestParam("id") String id, RedirectAttributes redirectAttributes) {

        model = menuService.getMenuItems(model); //get menu items

        System.out.println("idd: " + id);

        FinalOrder finalOrder0 = finalOrderRepository.getOne(Long.valueOf(id));
        finalOrder0.setKeepYourself(true);
        finalOrder0.setCompleted(true);
        finalOrderRepository.saveAndFlush(finalOrder0);

        List<FinalOrder> finalOrderList = finalOrderRepository.findAll();

        redirectAttributes.addFlashAttribute("listFinalOrder", finalOrderList);

        return "redirect:/accountUser";
    }

    @GetMapping("/accountDelGenre") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountDelGenre(Model model, Principal principal, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        return "/account/accountDelGenre";
    }

    @GetMapping("/accountDelGame") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountDelGame(Model model, Principal principal, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        return "/account/accountDelGame";
    }

    @GetMapping("/accountDelPlatform") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountDelPlatform(Model model, Principal principal, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        return "/account/accountDelPlatform";
    }

    //not use
    @GetMapping("/accountBalance") //можно зайти под админом
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_COURIER"})
    public String accountBalance(Model model, Principal principal, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        model.addAttribute("usr", userService.findByUsername(principal.getName()));
        return "/account/accountBalance";
    }

    @Autowired
    UserRepository userRepository;

    @PostMapping("/updateBalance")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String updateBalance(Model model, Principal principal, HttpServletRequest request,
                                @RequestParam("balance") String balance0
    ) {

        System.out.println("balance str: " + balance0);

        Double balance = 0.0;
        try {
            balance = Math.abs(Double.valueOf(balance0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("balance after abs: " + balance);

        User user = userService.findByUsername(principal.getName());
        user.setBalance(user.getBalance() + balance);
        user.getPassword();
        userRepository.save(user);

        return "redirect:/infoUser";
    }


}
