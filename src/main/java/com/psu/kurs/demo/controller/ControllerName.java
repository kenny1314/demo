package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
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
import java.util.*;

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
    LanguagesRepository languagesRepository;

    @Autowired
    AgeLimitsRepository ageLimitsRepository;

    @Autowired
    PublishersRepository publishersRepository;

    @Autowired
    ImagesTRepository imagesTRepository;

    @GetMapping("/testselect")
    public String testSelect(Model model) {


        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            List<String> stringList = new ArrayList<>();
            stringList.add("igor");
            stringList.add("gruntov");
            stringList.add("sergeevich");
            model.addAttribute("liststr", stringList);

            List<Languages> languagesList = languagesRepository.findAll();
            model.addAttribute("listlang", languagesList);

            logger.info("addgame");
        } catch (Exception ex) {

        }

        return "testSelectForm";
    }

    @PostMapping("/result")
    public @ResponseBody
    String getListSelect(@RequestParam("optionsLIstId") String selectedOption) {

        return " Selected: " + selectedOption;
    }


    @GetMapping("/pg")
    public String pg() {

        Products products = productsRepository.getOne(1L);
        Genres genres = products.getGenres();
        logger.info("pg: " + genres.toString());


        return "dialogs";
    }


    @GetMapping("/uppage")
    public String upPage() {

        return "upload";
    }


    @GetMapping("/addgame")
    public String addGame(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("addgame");

            List<Languages> languagesList = languagesRepository.findAll();
            model.addAttribute("languagesList", languagesList);

            List<AgeLimits> ageLimitsList = ageLimitsRepository.findAll();
            model.addAttribute("ageLimitsList", ageLimitsList);

            List<Genres> genresList = genresRepository.findAll();
            model.addAttribute("genresList", genresList);

            List<Publishers> publishersList = publishersRepository.findAll();
            model.addAttribute("publishersList", publishersList);

        } catch (Exception ex) {

        }
        return "addGame";
    }

    @PostMapping("/uploadGame")
    public @ResponseBody
    String uploadGame(@RequestParam("title") String title,
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
                //TODO imageP
                ImagesP imagesP = new ImagesP(images.getId(), images.getName(), images.getData(), images.getContentType(), images.getExtension());

                product.setImagesP(imagesP);

                productsRepository.save(product);

                logger.info("product.toString(): " + product.toString());

                return "form:\n" + "title: " + title + " lang: " + language + " platform: " + platform + " yearOfIssue:" + yearOfIssue +
                        " ageLimits: " + ageLimits + " genre: " + genre + " publisher: " + publisher +
                        " quantity: " + quantity + " oneDayPrice: " + oneDayPrice + " fullPrice: " + fullPrice +
                        " description: " + description +
                "  " + "name: " + imagesP.getName() + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
//
//
//        return "title: " + title + " lang: " + language + " platform: " + platform + " yearOfIssue:" + yearOfIssue +
//                " ageLimits: " + ageLimits + " genre: " + genre + " publisher: " + publisher +
//                " quantity: " + quantity + " oneDayPrice: " + oneDayPrice + " fullPrice: " + fullPrice +
//                " description: " + description;
    }


    @GetMapping("/addGenres")
    public String addGenres(Model model) {
        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("addgame");
        } catch (Exception ex) {

        }

        return "addGenres";
    }


    public Images getImagesClass(MultipartFile file, Long actualCount) throws IOException {
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


    @RequestMapping(value = "/uploadGenres", method = RequestMethod.POST)
    public @ResponseBody
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


                return "form:\n" +
                        "   " + name +
                        "  " + "name: " + name + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
    }


    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    public @ResponseBody
    String formUpload(@RequestParam("file") MultipartFile file,
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

                return "form:\n" +
                        "   " + name + "   " + manufacturer + "   " + relaseDate + "   " + generation + "   " + piecesSold + " " + cpu + "   "
                        + description + "    " + story +
                        "  " + "name: " + name + " Вы удачно загрузили изображение: " + file.getOriginalFilename() + " " + file.getContentType() + " rex: " + file.getContentType().split("\\/")[1] + "!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Вам не удалось загрузить " + file.getName() + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + file.getOriginalFilename() + " потому что файл пустой.";
        }
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

//                File file = new File("input2.jpg");

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

    //addplatform
    @GetMapping("/addplatform")
    public String addPlatform(Model model) {

        List<Platforms> platformsList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);
            logger.info("about");
        } catch (Exception ex) {

        }

        return "addPlatform";
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


    @GetMapping("/platform/{id}")
    public String getPlatformId(@PathVariable String id, Model model) {

        logger.info("id: " + id);

        Long idNew = 1L;

        if (id != null) {
            idNew = Long.valueOf(id);
        }

        List<Platforms> platformsList;
        Platforms platform;

        try {
            //для данных
            platformsList = platformsRepository.findAll();
            platform = platformsRepository.getOne(idNew);
            model.addAttribute("platforms", platformsList);
            model.addAttribute("platform", platform);
            logger.info("platform");
        } catch (Exception ex) {

        }

        return "platform";
    }


    @GetMapping("/platform")
    public String platform(Model model) {

        List<Platforms> platformsList;
        Platforms platform;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            platform = platformsRepository.getOne(1L);
            model.addAttribute("platforms", platformsList);
            model.addAttribute("platform", platform);
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
