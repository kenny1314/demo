package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.PoiTestWord;
import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.Address;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuService menuService;

    @Autowired
    OtherService otherService;


//    @Autowired
//    private ServletContext servletContext;
//
//    @GetMapping("/down")
//    public ResponseEntity<InputStreamResource> downloadFile1() throws IOException, FileNotFoundException {
//
//        String fileName = "3.xls";
//        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
//        logger.info("  "+mediaType);
//        System.out.println("fileName: " + fileName);
//        System.out.println("mediaType: " + mediaType);
//
//        File file = new File("C:\\Users\\Ihar_Hruntou\\eclipse-workspace\\blank\\3.xls");
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        return ResponseEntity.ok()
//                // Content-Disposition
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//                // Content-Type
//                .contentType(mediaType)
//                // Contet-Length
//                .contentLength(file.length()) //
//                .body(resource);
//    }


    @GetMapping(value = {"/t"})
    public @ResponseBody
    String getword() {

        List<Products> productsList = productsRepository.findAll();
        System.out.println("size: " + productsList.size());
        PoiTestWord poiTestWord = new PoiTestWord();
        try {
            poiTestWord.run(productsRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "word";
    }

    @GetMapping(value = {"/"})
    public String index(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "index";
    }

    @GetMapping(value = {"/getGameByPlatform/{id}"})
    public String getGameByPlatform(Model model, @PathVariable String id) {

        List<Products> newListProduct = new ArrayList<>();
        List<Products> productsList = productsRepository.findAll();

        for (Products prod : productsList) {
            System.out.println("idP:" + prod.getPlatforms().getId());
            if (prod.getPlatforms().getId() == Long.valueOf(id)) {
                newListProduct.add(prod);
            }
        }

        model = menuService.getMenuItems(model); //get menu items
        model.addAttribute("newListProduct", newListProduct);
        System.out.println("size prod:" + newListProduct.size());

        return "getGameByPlatform";
    }

    @GetMapping(value = {"/getGameByGenre/{id}"})
    public String getGameByGenre(Model model, @PathVariable String id) {

        List<Products> newListProduct = new ArrayList<>();
        List<Products> productsList = productsRepository.findAll();

        for (Products prod : productsList) {
//            System.out.println("idP:"+prod.getPlatforms().getId());
            if (prod.getGenres().getId() == Long.valueOf(id)) {
                newListProduct.add(prod);
            }
        }

        model = menuService.getMenuItems(model); //get menu items
        model.addAttribute("newListProduct", newListProduct);
        System.out.println("size prod:" + newListProduct.size());

        return "getGameByGenre";
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

    @GetMapping("/delusr")
    public @ResponseBody
    String delUsr() {
        userService.deleteUserWithRole(4L);
        return "test del usr";
    }

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

        Address address = new Address();

        address.setCity(city);
        address.setStreet(street);
        address.setFlatNumber(flatNumber);
        addressRepository.save(address);

        user.setAddress(address);
        userService.save(user);


        model.addAttribute("error", "Всё хорошо");
        return "redirect:/";
    }


    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

}
