package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.OtherService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    UserService userService;

    @Autowired
    RequestsRepository requestsRepository;

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    FinalOrderRepository finalOrderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    OtherService otherService;
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("---auth:"+auth);
//        if (auth != null){
//        logger.info("User '" + auth.getName() + "' attempted to access the protected URL: " + httpServletRequest.getRequestURI());
//    }
//
//    boolean isAdmin = httpServletRequest.isUserInRole("ROLE_USER");
//        System.out.println("http serv__:" + isAdmin);
//        System.out.println("uuuuser:" +httpServletRequest.getRemoteUser());
//
//    AdminController adminController = new AdminController();
////        System.out.println(Platforms.class.getCanonicalName());
////      logger.info("_______________size: "+adminController.getLastId(platformsRepository,Platforms.class.getCanonicalName()));



    @GetMapping(value = {"/", "index"})
    public String index(Model model, HttpServletRequest httpServletRequest) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        model = menuService.getMenuItems(model); //get menu items

        return "index";
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

    @GetMapping(value = {"/getGameByPlatform/{id}"})
    public String getGameByPlatform(Model model, @PathVariable String id) {
        List<Products> newListProduct = new ArrayList<>();
        List<Products> productsList = productsRepository.findAll();

        for (Products prod : productsList) {
//            System.out.println("idP:" + prod.getPlatforms().getId());
            if (prod.getPlatforms().getId() == Long.valueOf(id)) {
                newListProduct.add(prod);
            }
        }

        model = menuService.getMenuItems(model); //get menu items
        model.addAttribute("newListProduct", newListProduct);
        model.addAttribute("yourplatform", platformsRepository.getOne(Long.valueOf(id)).getName());
//        System.out.println("size prod:" + newListProduct.size());

        return "getGameByPlatform";
    }

    @GetMapping(value = {"/getGameByGenre/{id}"})
    public String getGameByGenre(Model model, @PathVariable String id) {
        List<Products> newListProduct = new ArrayList<>();
        List<Products> productsList = productsRepository.findAll();

        for (Products prod : productsList) {
//            System.out.println("idP:" + prod.getPlatforms().getId());
            if (prod.getGenres().getId() == Long.valueOf(id)) {
                newListProduct.add(prod);
            }
        }

        model = menuService.getMenuItems(model); //get menu items
        model.addAttribute("newListProduct", newListProduct);
        model.addAttribute("yourgenre", genresRepository.getOne(Long.valueOf(id)).getName());
//        System.out.println("size prod:" + newListProduct.size());

        return "getGameByGenre";
    }


    @GetMapping("/game/{id}")
    public String game(@PathVariable String id, Model model, HttpServletRequest request) {
        model = menuService.getMenuItems(model); //get menu items

        logger.info("game id: " + id);

        Products product;

        try {
            product = productsRepository.findById(Long.parseLong(id)).get();
            model.addAttribute("product", product);
            logger.info("product #" + id + ": " + product.toString());

            model.addAttribute("currentURL", otherService.getCurrentUrl(request));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "game";
    }


    @GetMapping("/platform/{id}")
    public String getPlatformId(@PathVariable String id, Model model) {
        model = menuService.getMenuItems(model); //get menu items

        logger.info("id: " + id);

        Long idNew = id != null ? Long.valueOf(id) : null;

        Platforms platform;

        try {
            //для данных
            platform = platformsRepository.getOne(idNew);
            model.addAttribute("platform", platform);
            logger.info("platform");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "platform";
    }

    //поиск игры
    @GetMapping("/productq")
    public String getProductByName(@RequestParam(value = "productName", required = true) String productName,
                                   Model model) {
        if (productName == null || productName == "") {
            logger.error("Пустой поиск");
            return "redirect:/";
        }
        Products product = productsRepository.findByTitle(productName);
        if (product == null) {
            logger.error("Нет такого продукта в базе данных");
            return "redirect:/errfind";
        }

        return "redirect:/game/" + product.getId();
    }

    @GetMapping("/errfind")
    public String errfind(Model model) {
        model = menuService.getMenuItems(model); //get menu items
        return "errfind";
    }


    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
