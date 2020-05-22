package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@EnableTransactionManagement
@Controller
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

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
    ImagesPRepository imagesPRepository;

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

    @GetMapping("/addgame")
    public String addGame(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        try {
            //для формы  ввода

            List<Languages> languagesList = languagesRepository.findAll();
            model.addAttribute("languagesList", languagesList);

            List<AgeLimits> ageLimitsList = ageLimitsRepository.findAll();
            model.addAttribute("ageLimitsList", ageLimitsList);

            List<Publishers> publishersList = publishersRepository.findAll();
            model.addAttribute("publishersList", publishersList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "/add/addGame";
    }

    @GetMapping("/addGenres")
    public String addGenres(Model model) {
        model = menuService.getMenuItems(model); //get menu items
        return "/add/addGenres";
    }

    @GetMapping("/addplatform")
    public String addPlatform(Model model) {
        model = menuService.getMenuItems(model); //get menu items
        return "/add/addPlatform";
    }

    //переделать
    @GetMapping("/delgame/{id}")
    public String delGameId(@PathVariable String id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws MalformedURLException {

        URL url = new URL(request.getHeader("Referer")); //where request is the HttpServletRequest
        String urlStr = url.getPath();

        if (urlStr.startsWith("/game/")) {
            urlStr = "/";
        }
        //если удаляем игру, то и удаляем и requests с этой игрой
        List<Requests> requestsList = requestsRepository.findAll();
        for (Requests req : requestsList) {
            if (req.getProducts().getId() == Long.valueOf(id)) {
                logger.info("совпадает" + req.getId());
                requestsRepository.deleteById(req.getId());
            }
        }
        redirectAttributes.addFlashAttribute("ok", true);

        productsRepository.deleteById(Long.valueOf(id));

        return "redirect:" + urlStr;
    }

    @Autowired
    ServletContext servletContext;

    //если есть игра с таким жанром, то не удаляем жанр
    @Transactional
    @GetMapping("/delGenreId/{id}")
    public String delGenreId(@PathVariable String id, Model model, RedirectAttributes redirectAttributes,
                             HttpServletRequest request) throws IOException, ServletException {

        URL url = new URL(request.getHeader("Referer")); //where request is the HttpServletRequest
        String urlStr = url.getPath();
//        System.out.println("lru: " + urlStr);

        model = menuService.getMenuItems(model); //get menu items

        String resqMeth = getDelGenre(id);

        if (resqMeth.equals("error")) {
            redirectAttributes.addFlashAttribute("error", true);
//            model.addAttribute("error", true);
        }
        if (resqMeth.equals("ok")) {
            System.out.println("ok page delGenreid");

            redirectAttributes.addFlashAttribute("ok", true);
//            model.addAttribute("ok", true);
        }
        String retStr = "redirect:" + urlStr;

        return retStr;
    }


    //если нельзя удалить то посылаем на страницу error
    public String getDelGenre(String id) {
        Long idGenre = null;
        try {
            idGenre = Long.valueOf(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        boolean trExistsOfGame = false;

        if (genresRepository.existsById(Long.valueOf(id))) {
            List<Products> productsList = productsRepository.findAll();

            for (Products prod : productsList) {
                if (prod.getGenres().getId().equals(idGenre)) {
                    trExistsOfGame = true;

                    return "error";
//                    return "delGenreError";
                    //перенаправить на стриницу ошибки или передать ошибку на текущую и вывести ошибку вверху
                }
            }
            if (!trExistsOfGame) {
                genresRepository.deleteById(idGenre);
                return "ok";
            }
        }

        return "ok";
    }


    //Если удаляем платформу, то меняем платформу на другую в игре
    //наверно нужно переделать, что не можем удалить платформу, если есть игра с такой платформой
    @GetMapping("/delplatform/{id}")
    public String delPlatformId(@PathVariable String id, Model model) {

        //TODO Если удаляем платформу, то Удаляем игру??
        //TODO должен работать триггер
//        List<Products> productsList = productsRepository.findAll();
//        for (Products prod : productsList) {
//            logger.info("id plaf:" + prod.getPlatforms().getId());
//            logger.info("id get:" + Long.valueOf(id));
//            if (prod.getPlatforms().getId().equals(Long.valueOf(id))) {
//                logger.info("Такая платформа ессть в продуктах");
//
//                prod.setIdd(1L);
//                prod.setPlatforms(platformsRepository.getOne(1L));
//                productsRepository.save(prod);
//            }
//        }

        if (platformsRepository.existsById(Long.valueOf(id))) {
            platformsRepository.deleteById(Long.valueOf(id));
        }

        return "redirect:/listplatforms";
    }


    @GetMapping("/delplatform1/{id}")
    public String delPlatformId1(@PathVariable String id, Model model, RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) throws MalformedURLException {

        URL url = new URL(request.getHeader("Referer")); //where request is the HttpServletRequest
        String urlStr = url.getPath();
        if (urlStr.startsWith("/platform")) {
            urlStr = "/listplatforms";
        }
        System.out.println("lru: " + urlStr);

        model = menuService.getMenuItems(model); //get menu items

        String resqMeth = getDelPlatform(id);

        if (resqMeth.equals("error")) {
            System.out.println("error page delplatform1");

            redirectAttributes.addFlashAttribute("error", true);
//            model.addAttribute("error", true);
        }
        if (resqMeth.equals("ok")) {
            System.out.println("ok page delplatform1");

            redirectAttributes.addFlashAttribute("ok", true);
//            model.addAttribute("ok", true);
        }
        String retStr = "redirect:" + urlStr;

        return retStr;

    }


    //если нельзя удалить то посылаем на страницу error
    public String getDelPlatform(String id) {
        Long idPlatform = null;
        try {
            idPlatform = Long.valueOf(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        boolean trExistsOfGame = false;

        if (platformsRepository.existsById(Long.valueOf(id))) {
            List<Products> productsList = productsRepository.findAll();

            for (Products prod : productsList) {
                if (prod.getGenres().getId().equals(idPlatform)) {
                    trExistsOfGame = true;

                    return "error";
                    //перенаправить на стриницу ошибки или передать ошибку на текущую и вывести ошибку вверху
                }
            }
            if (!trExistsOfGame) {
                platformsRepository.deleteById(idPlatform);
                return "ok";
            }
        }

        return "ok"; //не используется
    }


    public Long getLastId(JpaRepository<?, ?> jp, String str) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        //получение последнего id из списка платформ
        List<JpaRepository<?, ?>> productsList = (List<JpaRepository<?, ?>>) jp.findAll();

        int siz = productsList.size();
        List<Long> listSize = new ArrayList<>();
        Class cl = Class.forName(str);

        Long actualCount = -1L;

//        logger.info("size: " + siz);
        if (siz > 0) {
            for (Object obj : productsList) {
                if (str.equals("com.psu.kurs.demo.entity.FinalOrder")) {
                    FinalOrder finalOrder = (FinalOrder) obj;
                    listSize.add(finalOrder.getId());

                } else {
                    Field f = cl.getDeclaredField("id");
                    boolean flag1 = f.isAccessible();
                    f.setAccessible(true);

                    listSize.add((Long) f.get(obj));
                    f.setAccessible(flag1);
                }
            }

//            logger.info("collection print: "+listSize);
            System.out.println("list sizzeee:" + listSize);
            actualCount = Collections.max(listSize);
            logger.info("___max value in list: " + actualCount);
        } else {
            actualCount = 0L;
        }
        return actualCount;
    }

    @PostMapping("/uploadGame")
    public String uploadGame(@RequestParam("title") String title,
                             @RequestParam("langFormGame") String language,
                             @RequestParam("platformFormGame") String platform,
                             @RequestParam("yearOfIssue") String yearOfIssue,
                             @RequestParam("ageLimitFormGame") String ageLimits,
                             @RequestParam("genreFormGame") String genre,
                             @RequestParam("publisherFormGame") String publisher,
                             @RequestParam("quantity") String quantity,
                             @RequestParam("oneDayPrice") String oneDayPrice,
                             @RequestParam("fullPrice") String fullPrice,
                             @RequestParam("description") String description,
                             @RequestParam("file") MultipartFile file) {


        if (!file.isEmpty()) {
            try {
                //получение последнего id из списка платформ
                List<Products> productsList = productsRepository.findAll();

                int siz = productsList.size();
                List<Long> listSize = new ArrayList<>();

                Long actualCount = getLastId(productsRepository, Products.class.getCanonicalName());
                actualCount++;

                //получение последнего id из списка платформ

                Products product = new Products();
                product.setId(actualCount);
                product.setTitle(title);
                product.setLanguages(languagesRepository.getOne(Long.valueOf(language)));
                product.setPlatforms(platformsRepository.getOne(Long.valueOf(platform)));
                product.setYearOfIssue(Integer.parseInt(yearOfIssue));
                product.setAgeLimits(ageLimitsRepository.getOne(Long.valueOf(ageLimits)));
                product.setGenres(genresRepository.getOne(Long.valueOf(genre)));
                product.setPublishers(publishersRepository.getOne(Long.valueOf(publisher)));
                product.setQuantity(Integer.parseInt(quantity));
                product.setOneDayPrice(Double.parseDouble(oneDayPrice));
                product.setFullPrice(Double.parseDouble(fullPrice));
                product.setDescription(description);

                logger.info(product.toString());

                Images images = getImagesClass(file, actualCount);
                ImagesP imagesP = new ImagesP(images.getId(), images.getName(), images.getData(), images.getContentType(), images.getExtension());

                product.setImagesP(imagesP);

                imagesPRepository.save(imagesP);

                productsRepository.save(product);

                logger.info("\n====\nproduct.toString(): " + product.toString());
                //TODO может нужен редирект на страницу с тем, что добавляли
                return "redirect:/";
//                return "form:\n" + "title: " + title + " lang: " + language + " platform: " + platform + " yearOfIssue:" + yearOfIssue +
//                        " ageLimits: " + ageLimits + " genre: " + genre + " publisher: " + publisher +
//                        " quantity: " + quantity + " oneDayPrice: " + oneDayPrice + " fullPrice: " + fullPrice +
//                        " description: " + description +
//                        "  " + "name: " + imagesP.getName() + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
//                e.printStackTrace();
//                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
//            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/uploadGenres", method = RequestMethod.POST)
    public String formUploadGenres(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        if (!file.isEmpty()) {
            try {
                //получение последнего id из списка платформ
                List<Genres> genresList = genresRepository.findAll();
                int siz = genresList.size();
                List<Long> listSize = new ArrayList<>();

                Long actualCount = getLastId(genresRepository, Genres.class.getCanonicalName());
                actualCount++;
                System.out.println("actualCount" + actualCount);

                //получение последнего id из списка платформ
                Images images = getImagesClass(file, actualCount);
                ImagesG imagesG = new ImagesG(images.getId(), images.getName(), images.getData(), images.getContentType(), images.getExtension());

                Genres genres = new Genres(actualCount, name, imagesG);

                genresRepository.save(genres);


//                return "form:\n" +
//                        "   " + name +
//                        "  " + "name: " + name + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                e.printStackTrace();
//                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
//            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
        return "redirect:/genres";
    }

    //add console
    @RequestMapping(value = "/uploadPlatform", method = RequestMethod.POST)
    public String formUpload(@RequestParam("file") MultipartFile file,
                             @RequestParam("name") String name,
                             @RequestParam("manufacturer") String manufacturer,
                             @RequestParam("relaseDate") String relaseDate,
                             @RequestParam("generation") String generation,
                             @RequestParam("piecesSold") String piecesSold,
                             @RequestParam("cpu") String cpu,
                             @RequestParam("description") String description,
                             @RequestParam("story") String story

    ) {
        if (!file.isEmpty()) {
            try {
                List<Platforms> platformsList = platformsRepository.findAll();
                int siz = platformsList.size();
                List<Long> listSize = new ArrayList<>();

                Long actualCount = getLastId(platformsRepository, Platforms.class.getCanonicalName());
                actualCount++;

                //получение последнего id из списка платформ

                Images images = getImagesClass(file, actualCount);
                ImagesT imagesT = new ImagesT(images.getId(), images.getName(), images.getData(), images.getContentType(), images.getExtension());

                Platforms platform = new Platforms(actualCount, name, manufacturer, generation, relaseDate, piecesSold, cpu, description, story, imagesT);

                platformsRepository.save(platform);

                logger.info("platform.toString(): " + platform.toString());

                return "redirect:/listplatforms";
//                return "form:\n" +
//                        "   " + name + "   " + manufacturer + "   " + relaseDate + "   " + generation + "   " + piecesSold + " " + cpu + "   "
//                        + description + "    " + story +
//                        "  " + "name: " + name + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                e.printStackTrace();
//                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            logger.info("err");
//            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
        return "redirect:/listplatforms";
    }


    //service convert file to Images
    private Images getImagesClass(MultipartFile file, Long actualCount) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        BufferedImage bufferedImage = ImageIO.read(convFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, file.getContentType().split("\\/")[1], bos); //split to get an extension
        byte[] data = bos.toByteArray();
        logger.info("string genres" + data.toString());

        String encodedString = Base64.getEncoder().encodeToString(data);
        logger.info("str: " + encodedString.substring(0, 20));

        Images image = new Images(actualCount, convFile.getName().toString(), encodedString, file.getContentType(), file.getContentType().split("\\/")[1]);
        convFile.delete();
        return image;
    }
}
