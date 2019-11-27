package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.ReadFileToClass;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ControllerName {

    private static Logger logger = LoggerFactory.getLogger(ControllerName.class);

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;

    @Autowired
    LanguagesRepository languagesRepository;

    @Autowired
    AgeLimitsRepository ageLimitsRepository;

    @Autowired
    PublishersRepository publishersRepository;

    @Autowired
    ImagesTRepository imagesTRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    RequestsRepository requestsRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    FinalOrderRepository finalOrderRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    OtherService otherService;

    //registration order
    @PostMapping("/actionDefinition")
    public String actionDefinition(@RequestParam(name = "typeOfDelivery", required = false) String typeOfDelivery,
                                   @RequestParam(name = "finalPrice", required = false) String finalPrice, Model model
    ) {

        if (typeOfDelivery.equals("Курьер")) {

            return "forward:/createOrder";
        }
        if (typeOfDelivery.equals("Самовывоз")) {
            return "redirect:/storeSelection";
        }

        return "redirect:/basket";
    }

    //registration order
    @PostMapping("/createOrder")
    public String createOrder(Model model, Principal principal) {
        model = menuService.getMenuItems(model);

        User user = userService.findByUsername(principal.getName());

        Address address = new Address(user.getId(), "Новополоцк", "Молодёжная 69", "420");

        model.addAttribute("address", address);

        if (user != null) {
            Basket basket = basketRepository.getOne(userService.findByUsername(principal.getName()).getId());
            model.addAttribute("finalPrice", basket.getFinalPrice());
            logger.info("finalPrice: " + basket.getFinalPrice());
        } else {
            model.addAttribute("finalPrice", "notPrice");
        }

        return "createOrder";
    }
    //registration order
    //выбор магазина
    @GetMapping("/storeSelection")
    public String storeSelection(Model model, Principal principal) {

        model = menuService.getMenuItems(model); //get menu items


        return "store_selection";
    }

    //registration order
    //вывод значения с радиокнопки
    @PostMapping("/orderIsProcessed")
    public @ResponseBody
    String orderIsProcessed(@RequestParam(name = "fb", required = false) String radioValue) {

        return "fb: " + radioValue;
    }


    //хз что делает
    //TODO можно написать запрос sql sum
    @GetMapping("/createOrder1")
    public @ResponseBody
    String createOrder1(Principal principal) {

        User user = userService.findByUsername(principal.getName());

        FinalOrder finalOrder = new FinalOrder();

        finalOrder.setId(user.getId());
        finalOrder.setDate("new data");

        Basket basket = basketRepository.getOne(user.getId());

        List<Requests> requestsList = requestsRepository.findAll();

        double finalPrice = 0;
        for (Requests value : requestsList) {
            if (value.getBasket().getId() == basket.getId()) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
            }
        }
        basket.setFinalPrice(finalPrice);

        basketRepository.save(basket);

        finalOrderRepository.save(finalOrder);

        //выборка requests и выбор цены


        return "userid: " + user.getId();
    }



    //registration order
    @GetMapping("/delProdBask/{id}")
    public String delProdBasket(@PathVariable(value = "id", required = false) String id, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if (requestsRepository.existsById(Long.valueOf(id))) {
            requestsRepository.deleteById(Long.valueOf(id));

            List<Requests> requestsList = requestsRepository.findAll();
            Basket basket = basketRepository.getOne(user.getId());
            double finalPrice = 0;
            for (Requests value : requestsList) {
                if (value.getBasket().getId() == basket.getId()) {
                    finalPrice += value.getPrice() * value.getNumberOfDays();
                }
            }

            FinalOrder finalOrder = finalOrderRepository.getOne(user.getId());

            basket.setFinalPrice(finalPrice);
            finalOrder.setFinalPrice(finalPrice);

            basketRepository.save(basket);
            finalOrderRepository.save(finalOrder);
        } else {
            logger.info("product doesn't exist" + id);
        }

        return "redirect:/basket";
    }

    //registration order
    @GetMapping("/basket")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String basket(Model model, Principal principal, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        Basket basket;

        User user = userService.findByUsername(principal.getName());
        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

        List<Products> productsListBasket = new ArrayList<>();

        if (basket.getRequestsList().size() > 0) {
            for (Requests req : basket.getRequestsList()) {
                productsListBasket.add(req.getProducts());
            }

            model.addAttribute("requestsList", basket.getRequestsList());
            model.addAttribute("productsListBasket", productsListBasket);

        }

        model.addAttribute("currentURL", otherService.getCurrentUrl(request));
        model.addAttribute("finalPrice", basket.getFinalPrice());

        return "basket";
    }

    //registration order
    //сохранить в корзину
    @PostMapping("/testDB/{id}")
    @RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
    public String testDB(@PathVariable("id") String id, @RequestParam(name = "currentURL", required = false) String currentURL, @RequestParam("inputplus") String numberOfDays, Model model, Principal principal) {

        logger.info("principal: " + principal.getName());
        logger.info("_______________________________________ " + numberOfDays + "______________");

        model = menuService.getMenuItems(model); //get menu items

        Requests requests = new Requests();

        Products products = productsRepository.getOne(Long.valueOf(id));

        Date date = new Date();
        User user = userService.findByUsername(principal.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        requests.setDate(simpleDateFormat.format(new Date()));

        Basket basket = null;

        FinalOrder finalOrder = null;

        if (basketRepository.existsById(user.getId())) {
            basket = basketRepository.getOne(user.getId());
        } else {
            basket = new Basket();
            basket.setId(user.getId());
            basketRepository.save(basket);
        }

        if (finalOrderRepository.existsById(user.getId())) {
            finalOrder = finalOrderRepository.getOne(user.getId());
        } else {
            finalOrder = new FinalOrder();
            finalOrder.setId(user.getId());
            finalOrderRepository.save(finalOrder);
        }

        requests.setBasket(basket);

        requests.setNumberOfDays(Integer.parseInt(numberOfDays));
        requests.setPrice(products.getOneDayPrice());
        requests.setProducts(products);
        requests.setFinalOrder(finalOrder);

        requestsRepository.save(requests); //для заполнения таблицы

        boolean trAdd = false;

        List<Requests> requestsList = requestsRepository.findAll();

        if (requestsRepository.findAll().size() > 0) {
            for (int i = 0; i < requestsRepository.findAll().size(); i++) {
                if ((requestsList.get(i).getBasket().getId() == requests.getBasket().getId()) &&
                        (requestsList.get(i).getProducts().getId() == requests.getProducts().getId())) {

                    Long oldID = requestsList.get(i).getId();
                    logger.info("id old: " + oldID);
                    logger.info("((((Такая корзина и продукт уже есть уже есть");

                    basketRepository.save(basket);

                    requestsRepository.deleteById(oldID);
                    logger.info("drop the mic: " + oldID);
                    requestsRepository.save(requests);
                    logger.info("add to db");
                    trAdd = true;
                    break;
                }
            }
        }
        if (!trAdd) {
            logger.info("tradd: " + trAdd);
            basketRepository.save(basket);
            requestsRepository.save(requests);
        }

        double finalPrice = 0;
        for (Requests value : requestsList) {
            if (value.getBasket().getId() == basket.getId()) {
                finalPrice += value.getPrice() * value.getNumberOfDays();
            }
        }
        basket.setFinalPrice(finalPrice);
        finalOrder.setFinalPrice(finalPrice);
        basketRepository.save(basket);

        finalOrder.setDate(simpleDateFormat.format(new Date()));
        finalOrderRepository.save(finalOrder);
        logger.info("currentURL: " + currentURL);

        if (currentURL == null) {
            return "redirect:/";
        } else {
            return "redirect:/" + currentURL;
        }

    }







//    @GetMapping("/getplatforms")
//    public String getPlatforms(Model model) throws IOException {
////
////        Platforms platforms=new Platforms();
//////
////        platforms.setId(2L);
////        platforms.setName("Gruntik master system");
////        platforms.setCpu("gruntikcpu");
////        platformsRepository.save(platforms);
////
//        Long counter = 6L;
//        List<Platforms> platformsList = ReadFileToClass.getListFromFile();
//        for (Platforms pl : platformsList) {
//            pl.setId(1L + counter++);
//        }
//        platformsRepository.saveAll(platformsList);
//
//        return "getplatforms";
//    }




//    //??
//    @GetMapping("/vot")
//    public String affbout(Model model) {
//
//        List<Platforms> platformsList;
//
//        try {
//            //для меню
//            platformsList = platformsRepository.findAll();
//            model.addAttribute("platforms", platformsList);
//            logger.info("about");
//        } catch (Exception ex) {
//
//        }
//
//        return "about";
//    }




//    //??
//    @GetMapping("/platform")
//    public String platform(Model model) {
//
//        List<Platforms> platformsList;
//        Platforms platform;
//
//        try {
//            //для меню
//            platformsList = platformsRepository.findAll();
//            platform = platformsRepository.getOne(1L);
//            model.addAttribute("platforms", platformsList);
//            model.addAttribute("platform", platform);
//            logger.info("platform");
//        } catch (Exception ex) {
//
//        }
//
//        return "platform";
//    }
//
//    @GetMapping("/dialogs")
//    public String dialog(Model model) {
//        return "dialogs";
//    }

//    @GetMapping("/page")
//    public String page(Model model) {
//
//        List<Products> productsList;
//
//        try {
//            productsList = productsRepository.findAll();
//            model.addAttribute("productsList", productsList);
//            logger.info("sizeListProducts:" + productsList.size());
//            logger.info("product #1: " + productsList.get(1).toString());
//            logger.info("page");
//        } catch (Exception ex) {
//
//        }
//
//        return "page2";
//    }


//    @GetMapping("/game")
//    public String game(Model model) {
//        return "game";
//    }


}
