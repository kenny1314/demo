package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.Genres;
import com.psu.kurs.demo.entity.ImagesT;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.services.ReadFileToClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

@Controller
public class ControllerName {

    private static Logger logger = LoggerFactory.getLogger(ControllerName.class);

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RoRepository roRepository;


    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;


    @Autowired
    ImagesTRepository imagesTRepository;

    @GetMapping("/uppage")
    public String upPage() {

        return "upload";
    }




    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

//                File file = new File("input2.jpg");

                File convFile = new File( file.getOriginalFilename() );
                FileOutputStream fos = new FileOutputStream( convFile );
                fos.write( file.getBytes() );
                fos.close();

                BufferedImage bufferedImage = ImageIO.read(convFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, file.getContentType().split("\\/")[1], bos); //split to get an extension
                byte[] data = bos.toByteArray();
                logger.info("string" + data.toString());

                String encodedString = Base64.getEncoder().encodeToString(data);
                logger.info("str: " + encodedString);

                ImagesT imagesT = new ImagesT(0L, convFile.getName().toString(), encodedString,file.getContentType(),file.getContentType().split("\\/")[1]);

                logger.info("imagesT: " + imagesT.toString());

                imagesTRepository.save(imagesT);




                return "Вы удачно загрузили "+ convFile.getName() + " _____ "+file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] +"!";
            } catch (Exception e) {
                return "Вам не удалось загрузить " +   file.getName()+ " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
    }



//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file ) {
//
//
////        if (file.exists()) {
////            logger.info("file is empty");
////        }
//
//        try {
//            logger.info(file.getName());
//
//
////            File file = new File("input2.jpg");
//
//
//            byte[] bytes = file.getBytes();
//            BufferedOutputStream stream =
//                    new BufferedOutputStream(new FileOutputStream(new File(file.getName() + "-uploaded")));
//            stream.write(bytes);
//            stream.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//
//        return "dialogs";
//    }


    @GetMapping("/getimg")
    public String getImg(Model model) {

        return "imgpage";
    }


    @GetMapping("/getplatforms")
    public String getPlatforms(Model model) throws IOException {
//
//        Platforms platforms=new Platforms();
////
//        platforms.setId(2L);
//        platforms.setName("Gruntik master system");
//        platforms.setCpu("gruntikcpu");
//        platformsRepository.save(platforms);
//
        Long counter = 6L;
        List<Platforms> platformsList = ReadFileToClass.getListFromFile();
        for (Platforms pl : platformsList) {
            pl.setId(1L + counter++);
        }
        platformsRepository.saveAll(platformsList);

        return "getplatforms";
    }


    @GetMapping("/genres")
    public String genres(Model model) {

        List<Platforms> platformsList;
        List<Genres> genresList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);

            genresList = genresRepository.findAll();
            model.addAttribute("genresList", genresList);

            logger.info("sizeListProducts:" + genresList.size());
            logger.info("genres");
        } catch (Exception ex) {

        }

        return "genres";
    }


    @GetMapping("/vot")
    public String affbout(Model model) {

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


    @GetMapping("/listplatforms")
    public String listplatforms(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("about");
        } catch (Exception ex) {

        }

        return "listplatforms";
    }

    @GetMapping("/delivery")
    public String delivery(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("about");
        } catch (Exception ex) {

        }

        return "delivery";
    }


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


//    @GetMapping("/game")
//    public String game(Model model) {
//        return "game";
//    }

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
