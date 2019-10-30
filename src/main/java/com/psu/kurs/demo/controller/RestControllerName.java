package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.EmployeeRepository;
import com.psu.kurs.demo.dao.ImagesTRepository;
import com.psu.kurs.demo.dao.PlatformsRepository;
import com.psu.kurs.demo.entity.Employee;
import com.psu.kurs.demo.entity.ImagesT;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.services.GetImageBufferBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;


@RestController
public class RestControllerName {

    private static Logger logger = LoggerFactory.getLogger(RestControllerName.class);

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    ImagesTRepository imagesTRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping("/testp")
    public String testP() {

        return "toString\n"+platformsRepository.getOne(1L).toString();


//
//        ImagesT imagesT = imagesTRepository.getOne(1L);
//
//        Platforms platforms = platformsRepository.getOne(1L);
//        platforms.setImagesT(imagesT);
//        platformsRepository.save(platforms);

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
        logger.info("string" + data.toString());

        String encodedString = Base64.getEncoder().encodeToString(data);
        logger.info("str: " + encodedString);

        ImagesT imagesT = new ImagesT(9L, file.getName().toString(), encodedString, "nULL1", "nULL2");

        logger.info("imagesT: " + imagesT.toString());

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


    @RequestMapping(value = "/getimg/{id}", method = RequestMethod.GET)
    public void getImageBD(@RequestHeader(required = false, value = "Content-Type") String contextHeader,
                           HttpServletResponse response, @PathVariable String id) throws IOException {
        logger.info("id: " + id);

//        BufferedImage bufferedImage = GetImageBufferBD.getImgThroughID(1L);
//GetImageBufferBD.getImgThroughID(1L);

        logger.info("mmm: " + platformsRepository.findAll().size());
        logger.info("mmm: " + imagesTRepository.findAll().size());

//        GetImageBufferBD.getImgThroughID(imagesTRepository,1L);


//        GetImgTry2 getImg=new GetImgTry2();
//
//        logger.info("getimgtry2: "+getImg.getStr());
//        getImg.getImg();
        Long idNew = 1L;
//
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