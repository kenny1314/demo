package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.*;
import com.psu.kurs.demo.services.GetImageBufferBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
public class RestControllerName {

    private static Logger logger = LoggerFactory.getLogger(RestControllerName.class);

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    ImagesTRepository imagesTRepository;

    @Autowired
    GenresRepository genresRepository;

    @Autowired
    ImagesGRepository imagesGRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ImagesPRepository imagesPRepository;

    @Autowired
    AgeLimitsRepository ageLimitsRepository;

    @Autowired
    ManRepository manRepository;

    @Autowired
    GirlsRepository girlsRepository;

    @RequestMapping("/api/platforms")
    public List<Man> findAllPlatforms() {

//        Man man = new Man();
//        man.setNameMan("Igor");
//        manRepository.save(man);


//        if (manRepository.findAll() != null) {
//            List<Man> manList = manRepository.findAll();
//
//            long inxM = manList.get(manList.size() - 1).getId();
//        }

//        Girls girls = new Girls();
//        girls.setNameGirl("Masha");
//        girls.setMan(manRepository.getOne(1L));
//        girlsRepository.save(girls);
//
//        System.out.println(manRepository.getOne(1L).getGirlsList());
//
//        List<Girls> girlsList = new ArrayList<>();
//        girlsRepository.getOne(1L);
//
//        Man man1 = manRepository.getOne(1L);
        List<Girls> girlsList2 = new ArrayList<>();
//        girlsList.add(girlsRepository.getOne(1L));
////        man1.setGirlsList(girlsList);
////        man1.setGirlsList(girlsRepository.findAll());
//        manRepository.save(man1);


//        List<Man> manList = manRepository.findAll();
//
//        girlsList2.add(girlsRepository.getOne(1L));
//
//        Man man2=manRepository.getOne(1L);
//        man2.setGirlsList(girlsList2);

        Girls girls = girlsRepository.getOne(1L);

//        Gson gson = new Gson();
////
////
//         String json = gson.toJson(girls);
////
//        // Java objects to File
//        try (FileWriter writer = new FileWriter("D:\\man2.json")) {
//            gson.toJson(json, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // compact print
//        Gson gson = new Gson();
//
        Tabi tabi = new Tabi();
        tabi.setId(2L);
        tabi.setName("igor");
//
//
//        Girls girls1=girlsRepository.getOne(1L);
//        System.out.println(girls1.toString());
//
//        String lang = gson.toJson(tabi);
//        String lang2 = gson.toJson(girls1,Girls.class);
//
////        System.out.println(lang);
//        System.out.println(lang2);

//        ObjectMapper Obj = new ObjectMapper();
//
//        try {
//
//            // get Oraganisation object as a json string
//            String jsonStr = Obj.writeValueAsString(girls);
//
//            // Displaying JSON String
//            System.out.println(jsonStr);
//        }
//
//        catch (IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(manRepository.getOne(1L).getGirlsList().size());


        return manRepository.findAll();
    }


    @RequestMapping("/searchq2")
    public List<Products> search2(@RequestParam(value = "value") String value) throws IOException {
        try {
            List<Products> products = productsRepository.findByTitleContainsIgnoreCase(value);
            return products;
        } catch (Exception e) {
            return null;
        }
    }


    //костыль
//    @Deprecated
    @RequestMapping("/searchq")
//    public List<Products> search(@RequestParam(value = "value") String value) throws IOException {
    public List<String> search(@RequestParam(value = "value") String value) throws IOException {
        List<String> lists = new ArrayList<>();
        try {
            List<Products> products = productsRepository.findByTitleContainsIgnoreCase(value);
            for (Products pr : products) {
                lists.add(pr.getTitle());
            }
            return lists;
        } catch (Exception e) {
            return null;
        }
    }


    //не надо использовать //загрузка одного файла в базу данных
    @RequestMapping(value = "/gruntikimg", method = RequestMethod.GET)
    public void getImage(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                         HttpServletResponse response) throws IOException {


        File file = new File("input2.jpg");

        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", bos);
        byte[] data = bos.toByteArray();
//        logger.info("string" + data.toString());

        String encodedString = Base64.getEncoder().encodeToString(data);
//        logger.info("str: " + encodedString);

        ImagesT imagesT = new ImagesT(9L, file.getName().toString(), encodedString, "nULL1", "nULL2");

//        logger.info("imagesT: " + imagesT.toString());

        imagesTRepository.save(imagesT);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);


//            String text = "gruntikimg";
//            BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2d = img.createGraphics();
//            Font font = new Font("Arial", Font.PLAIN, 30);
//            g2d.setFont(font);
//            g2d.setColor(Color.RED);
//            g2d.drawString(text, 128, 128);
//            g2d.dispose();


        response.setContentType("image/jpeg");
        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());

        return;
    }

    //получить изображение Продукта
    @RequestMapping(value = "/getimgp/{id}", method = RequestMethod.GET)
    public void getImageBDProducts(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                                   HttpServletResponse response, @PathVariable String id) throws IOException {
        logger.info("id: " + id);

        logger.info("productsRepository.size(): " + productsRepository.findAll().size());
        logger.info("imagesRepository.size(): " + imagesPRepository.findAll().size());

        Long idNew = 1L;

        if (id != null) {
            idNew = Long.valueOf(id);
        }

        ImagesP imagesP = imagesPRepository.getOne(idNew);
        BufferedImage bufferedImage = GetImageBufferBD.getImgThroughID(imagesP);

        response.setContentType(imagesP.getContentType());
        ImageIO.write(bufferedImage, imagesP.getExtension(), response.getOutputStream());

        return;
    }

    //получить изображение жанры
    @RequestMapping(value = "/getimgg/{id}", method = RequestMethod.GET)
    public void getImageBDGenres(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                                 HttpServletResponse response, @PathVariable String id) throws IOException {
        logger.info("id: " + id);

        logger.info("mmm: " + genresRepository.findAll().size());
        logger.info("mmm: " + imagesGRepository.findAll().size());

        Long idNew = 1L;

        if (id != null) {
            idNew = Long.valueOf(id);
        }

        ImagesG imagesG = imagesGRepository.getOne(idNew);
        BufferedImage bufferedImage = GetImageBufferBD.getImgThroughID(imagesG);

        response.setContentType(imagesG.getContentType());
        ImageIO.write(bufferedImage, imagesG.getExtension(), response.getOutputStream());

        return;
    }


    //получить изображение платформы
    @RequestMapping(value = "/getimg/{id}", method = RequestMethod.GET)
    public void getImageBD(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                           HttpServletResponse response, @PathVariable String id) throws IOException {
        logger.info("id: " + id);

        logger.info("mmm: " + platformsRepository.findAll().size());
        logger.info("mmm: " + imagesTRepository.findAll().size());

        Long idNew = 1L;

        if (id != null) {
            idNew = Long.valueOf(id);
        }

        ImagesT imagesT = imagesTRepository.getOne(idNew);
        BufferedImage bufferedImage = GetImageBufferBD.getImgThroughID(imagesT);

        response.setContentType(imagesT.getContentType());
        ImageIO.write(bufferedImage, imagesT.getExtension(), response.getOutputStream());

        return;
    }

}