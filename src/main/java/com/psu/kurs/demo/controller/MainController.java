package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.entity.User;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class MainController {

    private static Logger logger = LoggerFactory.getLogger(MainController.class);

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

    @GetMapping(value = {"/"})
    public String index(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "index";
    }

    @GetMapping("/game/{id}")
    public String game(@PathVariable String id, Model model, HttpServletRequest request) {

        model = menuService.getMenuItems(model); //get menu items

        logger.info("gameID");
        logger.info("game id: " + id);

        Products product;

//        String str = new String();
        try {
            product = productsRepository.findById(Long.parseLong(id)).get();
            model.addAttribute("product", product);
            logger.info("product #" + id + ": " + product.toString());

//            str = request.getRequestURL().toString();

            model.addAttribute("currentURL", otherService.getCurrentUrl(request));

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return "game";
    }


    @GetMapping("/genres")
    public String genres(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "genres";
    }

    @GetMapping("/listplatforms")
    public String listplatforms(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "listplatforms";
    }

    @GetMapping("/delivery")
    public String delivery(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "delivery";
    }


    @GetMapping("/about")
    public String about(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "about";
    }


    @GetMapping("/platform/{id}")
    public String getPlatformId(@PathVariable String id, Model model) {

        model = menuService.getMenuItems(model); //get menu items

        logger.info("id: " + id);

        Long idNew = 1L;

        if (id != null) {
            idNew = Long.valueOf(id);
        }

        Platforms platform;

        try {
            //для данных
            platform = platformsRepository.getOne(idNew);
            model.addAttribute("platform", platform);
            logger.info("platform");
        } catch (Exception ex) {

        }

        return "platform";
    }


    @GetMapping("/login")
    public String login(Model model) {
        model = menuService.getMenuItems(model); //get menu items
        return "login";
    }

    @GetMapping("/registration")
    public String reg(Model model) {
        logger.info("get registration");

        model = menuService.getMenuItems(model); //get menu items

        return "/registration";
    }

    //обработка регистрации
    @PostMapping("/registration")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
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
        userService.save(user);
        model.addAttribute("error", "Всё хорошо");
        return "redirect:/";
    }


    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
