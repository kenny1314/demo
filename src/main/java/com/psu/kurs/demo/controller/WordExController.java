package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.PoiTestExel;
import com.psu.kurs.demo.PoiTestWord;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.services.MediaTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class WordExController {
    private static Logger logger = LoggerFactory.getLogger(WordExController.class);

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/downExel")
    public ResponseEntity<InputStreamResource> downloadExel() throws IOException, FileNotFoundException {

        PoiTestExel poiTestExel = new PoiTestExel();
        try {
            poiTestExel.run(productsRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = "products.xls";
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        logger.info("  " + mediaType);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File("D:\\demo\\products.xls");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/downWord")
    public ResponseEntity<InputStreamResource> downloadWord() throws IOException, FileNotFoundException {

        PoiTestWord poiTestWord = new PoiTestWord();
        try {
            poiTestWord.run(productsRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = "products.docx";
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        logger.info("  " + mediaType);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File("D:\\demo\\products.docx");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/e")
    public @ResponseBody
    String getWordExel() {

        PoiTestExel poiTestExel = new PoiTestExel();
        try {
            poiTestExel.run(productsRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "exel";
    }

    @GetMapping("/t")
    public @ResponseBody
    String getWordFile() {

        PoiTestWord poiTestWord = new PoiTestWord();
        try {
            poiTestWord.run(productsRepository);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "word";
    }
}
