package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.ImagesG;
import com.psu.kurs.demo.entity.ImagesP;
import com.psu.kurs.demo.entity.ImagesT;
import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.services.GetImageBufferBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


@RestController
public class RestControllerName {
//igor
    private static Logger logger = LoggerFactory.getLogger(RestControllerName.class);

    @Autowired
    ImagesTRepository imagesTRepository;

    @Autowired
    ImagesPRepository imagesPRepository;

    @Autowired
    ImagesGRepository imagesGRepository;


    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;


    //поиск игры
    @RequestMapping("/searchq2")
    public List<Products> search2(@RequestParam(value = "value") String value) throws IOException {
        try {
            List<Products> products = productsRepository.findByTitleContainsIgnoreCase(value);
            return products;
        } catch (Exception e) {
            return null;
        }
    }


    //получить изображение Продукта
    @RequestMapping(value = "/getimgp/{id}", method = RequestMethod.GET)
    public void getImageBDProducts(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                                   HttpServletResponse response, @PathVariable String id) throws IOException {
        logger.info("id: " + id);

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