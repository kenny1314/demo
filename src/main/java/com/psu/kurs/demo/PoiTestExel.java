package com.psu.kurs.demo;

import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.entity.Products;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiTestExel {

    public void run(ProductsRepository productsRepository) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Products");
        int rowNum = 0;
        System.out.println("Creating excel");
        List<Products> productsList = productsRepository.findAll();

        int colNum1 = 0;
        Row row = sheet.createRow(rowNum++);
        Cell cell = null;
        row.createCell(colNum1++).setCellValue("ID");
        row.createCell(colNum1++).setCellValue("Title");
        row.createCell(colNum1++).setCellValue("Full price");
        row.createCell(colNum1++).setCellValue("One day price");
        row.createCell(colNum1++).setCellValue("Quantity");
        row.createCell(colNum1++).setCellValue("Year of issue");
        row.createCell(colNum1++).setCellValue("Age limit");
        row.createCell(colNum1++).setCellValue("Genres");
        row.createCell(colNum1++).setCellValue("Language");
        row.createCell(colNum1++).setCellValue("Platform");
        row.createCell(colNum1++).setCellValue("Publishefffr");

        int rowNum1 = 1;
        for (Products prod : productsList) {
            Row row1 = sheet.createRow(rowNum1++);
            int colNum = 0;
            row1.createCell(colNum++).setCellValue(prod.getId().intValue());
            row1.createCell(colNum++).setCellValue(prod.getTitle());
            row1.createCell(colNum++).setCellValue(prod.getFullPrice().toString());
            row1.createCell(colNum++).setCellValue(prod.getOneDayPrice().toString());
            row1.createCell(colNum++).setCellValue(prod.getQuantity());
            row1.createCell(colNum++).setCellValue(prod.getYearOfIssue());
            row1.createCell(colNum++).setCellValue(prod.getAgeLimits().getYear());
            row1.createCell(colNum++).setCellValue(prod.getGenres().getName());
            row1.createCell(colNum++).setCellValue(prod.getLanguages().getName());
            row1.createCell(colNum++).setCellValue(prod.getPlatforms().getName());
            row1.createCell(colNum).setCellValue(prod.getPublishers().getName());
            System.out.println("++");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("products.xls");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }


}
