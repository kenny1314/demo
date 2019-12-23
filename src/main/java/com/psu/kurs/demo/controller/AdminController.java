package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.MenuService;
import com.psu.kurs.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

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
            //для формы

            List<Languages> languagesList = languagesRepository.findAll();
            model.addAttribute("languagesList", languagesList);

            List<AgeLimits> ageLimitsList = ageLimitsRepository.findAll();
            model.addAttribute("ageLimitsList", ageLimitsList);

            List<Publishers> publishersList = publishersRepository.findAll();
            model.addAttribute("publishersList", publishersList);

        } catch (Exception ex) {

        }
        return "addGame";
    }


    //admin
    @GetMapping("/addGenres")
    public String addGenres(Model model) {
        model = menuService.getMenuItems(model); //get menu items

        return "addGenres";
    }

    //admin
    //addplatform
    @GetMapping("/addplatform")
    public String addPlatform(Model model) {

        model = menuService.getMenuItems(model); //get menu items

        return "addPlatform";
    }


    @GetMapping("/delgame/{id}")
    public @ResponseBody
    String delGameId(@PathVariable String id, Model model) {

        //если удаляем игру, то и удаляем и requests с этой игрой

        List<Requests> requestsList = requestsRepository.findAll();
        for (Requests req : requestsList) {
            if (req.getProducts().getId() == Long.valueOf(id)) {
                logger.info("совпадает" + req.getId());
                requestsRepository.deleteById(req.getId());
            }
        }

        productsRepository.deleteById(Long.valueOf(id));

        return "Так-с ";
    }


    @GetMapping("/delplatform/{id}")
    public String delPlatformId(@PathVariable String id, Model model) {

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

                Long actualCount = -1L;

                logger.info("size: " + siz);
                if (siz > 0) {
                    for (Products prod : productsList) {
                        listSize.add(prod.getId());
                    }

                    actualCount = Collections.max(listSize);
                    logger.info("___max value in list: " + actualCount);
                    actualCount++;
                    logger.info("___next id in these tables: " + actualCount);
                } else {
                    actualCount = 0L;
                }

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

//                imagesPre

                //

                imagesPRepository.save(imagesP);

                productsRepository.save(product);

                logger.info("product.toString(): " + product.toString());
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
    public
    String formUploadGenres(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        if (!file.isEmpty()) {
            try {

                //получение последнего id из списка платформ
                List<Genres> genresList = genresRepository.findAll();
                int siz = genresList.size();
                List<Long> listSize = new ArrayList<>();

                Long actualCount = -1L;

                logger.info("size: " + siz);
                if (siz > 0) {
                    for (Genres gr : genresList) {
                        listSize.add(gr.getId());
                    }

                    actualCount = Collections.max(listSize);
                    logger.info("___max value in list: " + actualCount);
                    actualCount++;
                    logger.info("___next id in these tables: " + actualCount);
                } else {
                    actualCount = 0L;
                }

                Images images = getImagesClass(file, actualCount);
                ImagesG imagesG = new ImagesG(images.getId(), images.getName(), images.getData(), images.getContentType(), images.getExtension());

                Genres genres = new Genres(actualCount, name, imagesG);

                genresRepository.save(genres);

                logger.info("genres.toString(): " + genres.toString());


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
    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
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

                //получение последнего id из списка платформ
                List<Platforms> platformsList = platformsRepository.findAll();
                int siz = platformsList.size();
                List<Long> listSize = new ArrayList<>();

                Long actualCount = -1L;

                logger.info("size: " + siz);
                if (siz > 0) {
                    for (Platforms pl : platformsList) {
                        listSize.add(pl.getId());
                    }

                    actualCount = Collections.max(listSize);
                    logger.info("___max value in list: " + actualCount);
                    actualCount++;
                    logger.info("___next id in these tables: " + actualCount);
                } else {
                    actualCount = 0L;
                }

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


    //service
    private Images getImagesClass(MultipartFile file, Long actualCount) throws IOException {
        byte[] bytes = file.getBytes();

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
        logger.info("str: " + encodedString);

        Images image = new Images(actualCount, convFile.getName().toString(), encodedString, file.getContentType(), file.getContentType().split("\\/")[1]);
        convFile.delete();
        return image;
    }


    //consoles??
    //что это?
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File convFile = new File(file.getOriginalFilename());
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close();

                BufferedImage bufferedImage = ImageIO.read(convFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, file.getContentType().split("\\/")[1], bos); //split to get an extension
                byte[] data = bos.toByteArray();
                logger.info("string" + data.toString());

                String encodedString = Base64.getEncoder().encodeToString(data);
                logger.info("str: " + encodedString);

                ImagesT imagesT = new ImagesT(0L, convFile.getName().toString(), encodedString, file.getContentType(), file.getContentType().split("\\/")[1]);

                logger.info("imagesT: " + imagesT.toString());

                imagesTRepository.save(imagesT);

                return "Вы удачно загрузили " + convFile.getName() + " _____ " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
    }
}
