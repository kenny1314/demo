package com.psu.kurs.demo.services;

import com.psu.kurs.demo.entity.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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
