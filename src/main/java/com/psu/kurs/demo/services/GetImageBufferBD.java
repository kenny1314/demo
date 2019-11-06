package com.psu.kurs.demo.services;

import com.psu.kurs.demo.controller.RestControllerName;
import com.psu.kurs.demo.dao.ImagesTRepository;
import com.psu.kurs.demo.dao.PlatformsRepository;
import com.psu.kurs.demo.entity.Images;
import com.psu.kurs.demo.entity.ImagesT;
import com.psu.kurs.demo.entity.Platforms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

public class GetImageBufferBD {

    private static Logger logger = LoggerFactory.getLogger(GetImageBufferBD.class);


    public static BufferedImage getImgThroughID(Images imagesT) {

        logger.info(imagesT.toString());

        String decodeString = imagesT.getData();
        byte[] decodedBytes = Base64.getDecoder().decode(decodeString);
        InputStream in = new ByteArrayInputStream(decodedBytes);

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

}
