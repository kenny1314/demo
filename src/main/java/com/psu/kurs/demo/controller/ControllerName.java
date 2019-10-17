package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.PlatformsRepository;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.dao.RoRepository;
import com.psu.kurs.demo.dao.UsersRepository;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ControllerName {

    private static Logger logger = LoggerFactory.getLogger(ControllerName.class);


//    @Autowired
//    GameServices gameServices;


    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoRepository roRepository;


    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @GetMapping("/about")
    public String about(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("about");
        } catch (Exception ex) {

        }
        
        return "about";
    }
    @GetMapping("/platform")
    public String platform(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("platform");
        } catch (Exception ex) {

        }

        return "platform";
    }

    @GetMapping("/dialogs")
    public String dialog(Model model) {
        return "dialogs";
    }

    @GetMapping("/page")
    public String page(Model model) {

        List<Products> productsList;

        try {
            productsList = productsRepository.findAll();
            model.addAttribute("productsList", productsList);
            logger.info("sizeListProducts:" + productsList.size());
            logger.info("product #1: " + productsList.get(1).toString());
            logger.info("page");
        } catch (Exception ex) {

        }

        return "page2";
    }

    @GetMapping("/")
    public String index(Model model) {


        List<Platforms> platformsList;
        List<Products> productsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);

            productsList = productsRepository.findAll();
            model.addAttribute("productsList", productsList);
            logger.info("sizeListProducts:" + productsList.size());
//            logger.info("product #1: " + productsList.get(1).toString());
            logger.info("index");
        } catch (Exception ex) {

        }


        return "index";
    }

    @GetMapping("/game")
    public String game(Model model) {
        return "game";
    }

    @GetMapping("/game/{id}")
    public String game(@PathVariable String id, Model model) {
        logger.info("gameID");
        logger.info("game id: " + id);

        Products product;
        List<Products> productsList;
        List<Platforms> platformsList;

        try {
            product = productsRepository.findById(Long.parseLong(id)).get();
            model.addAttribute("product", product);
            logger.info("product #" + id + ": " + product.toString());

            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);


        } catch (Exception ex) {
            ex.printStackTrace();

        }


        return "game";
    }


}
