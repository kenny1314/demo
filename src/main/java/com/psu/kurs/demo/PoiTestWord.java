package com.psu.kurs.demo;

import com.psu.kurs.demo.dao.FinalOrderRepository;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.entity.FinalOrder;
import com.psu.kurs.demo.entity.Products;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public class PoiTestWord {

    public void run(ProductsRepository productsRepository) throws IOException {
        List<Products> productsList = productsRepository.findAll();
        System.out.println("size: " + productsList.size());

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("products.docx"));

        //create paragraph
        XWPFParagraph paragraph = document.createParagraph();

        //Set alignment paragraph to RIGHT
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("Отчёт о заказах");

        int rowQuant = productsList.size() * 12 + 1; //количество строк для вывода +1

        XWPFTable table = document.createTable(rowQuant, 2);

        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));

        table.getRow(0).getCell(0).setText("Name");
        table.getRow(0).getCell(1).setText("Value");

        int counter = 1;
        int counter2 = 1;
        int inx = 0;
        for (Products prod : productsList) {
            table.getRow(counter++).getCell(1).setText(prod.getId().toString());

            table.getRow(counter++).getCell(1).setText(prod.getTitle());
            table.getRow(counter++).getCell(1).setText(prod.getFullPrice().toString());
            table.getRow(counter++).getCell(1).setText(prod.getOneDayPrice().toString());
            table.getRow(counter++).getCell(1).setText(prod.getQuantity().toString());
            table.getRow(counter++).getCell(1).setText(String.valueOf(prod.getYearOfIssue()));
            table.getRow(counter++).getCell(1).setText(String.valueOf(prod.getAgeLimits().getYear()));
            table.getRow(counter++).getCell(1).setText(prod.getGenres().getName());
            table.getRow(counter++).getCell(1).setText(prod.getLanguages().getName());
            table.getRow(counter++).getCell(1).setText(prod.getPlatforms().getName());
            table.getRow(counter++).getCell(1).setText(prod.getPublishers().getName());

            table.getRow(counter2++).getCell(0).setText("ID");
            table.getRow(counter2++).getCell(0).setText("Title");
            table.getRow(counter2++).getCell(0).setText("Full price");
            table.getRow(counter2++).getCell(0).setText("One day price");
            table.getRow(counter2++).getCell(0).setText("Quantity");
            table.getRow(counter2++).getCell(0).setText("Year of issue");
            table.getRow(counter2++).getCell(0).setText("Age limit");
            table.getRow(counter2++).getCell(0).setText("Genres");
            table.getRow(counter2++).getCell(0).setText("Language");
            table.getRow(counter2++).getCell(0).setText("Platform");
            table.getRow(counter2++).getCell(0).setText("Publisher");

            mergeCellsHorizontal(table, counter, 0, 1);
            counter++;
            counter2++;
        }

        table.setTableAlignment(TableRowAlign.CENTER);

        document.write(out);
        out.close();
        System.out.println("alignparagraph.docx written successfully");
    }

    public void run1(FinalOrderRepository finalOrderRepository) throws IOException {
        List<FinalOrder> finalOrdersList = finalOrderRepository.findAll();
        System.out.println("size: " + finalOrdersList.size());

        //Blank Document
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("orders.docx"));

        //create paragraph
        XWPFParagraph paragraph = document.createParagraph();

        //Set alignment paragraph to RIGHT
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("Отчёт о заказах");

        //что такое 12?
        int rowQuant = finalOrdersList.size() + 1; //количество строк для вывода +1

        XWPFTable table = document.createTable(rowQuant, 4);

        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));
        table.getRow(0).getCell(2).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));
        table.getRow(0).getCell(3).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));

        table.getRow(0).getCell(0).setText("ID");
        table.getRow(0).getCell(1).setText("ID User");
        table.getRow(0).getCell(2).setText("Date");
        table.getRow(0).getCell(3).setText("Sum");

        int counter = 1;
        int counter2 = 1;
        int inx = 0;
        for (int i = 0; i < finalOrdersList.size(); i++) {
            FinalOrder finalOrder = finalOrdersList.get(i);

            table.getRow(counter).getCell(0).setText(finalOrder.getId().toString());
            table.getRow(counter).getCell(1).setText(String.valueOf(finalOrder.getUser().getId()));
            table.getRow(counter).getCell(2).setText(finalOrder.getDate().toString());
            table.getRow(counter).getCell(3).setText(String.valueOf(finalOrder.getFinalPrice()));

            counter++;
        }

        table.setTableAlignment(TableRowAlign.CENTER);

        document.write(out);
        out.close();
        System.out.println("alignparagraph.docx written successfully");
    }


    private void mergeCellsHorizontal(XWPFTable table, int row, int startCol, int endCol) {
        for (int cellIndex = startCol; cellIndex <= endCol; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == startCol) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }


}
