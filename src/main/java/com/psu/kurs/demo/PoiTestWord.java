package com.psu.kurs.demo;

import com.psu.kurs.demo.dao.*;
import com.psu.kurs.demo.entity.AgeLimits;
import com.psu.kurs.demo.entity.Products;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        //create parafffgraph
        XWPFParagraph paragraph = document.createParagraph();

        //Set alignment paragraph to RIGHT
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("Отчёт по продуктах в базе данных");


        int rowQuant = productsList.size() * 12 + 1; //количество строк для вывода +1

        XWPFTable table = document.createTable(rowQuant, 2);

        table.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));
        table.getRow(0).getCell(1).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(2500));


        table.getRow(0).getCell(0).setText("Name");
        table.getRow(0).getCell(1).setText("Value");

        int counter = 1;
        int counter2 = 1;
        int inx = 0;
        for (int i = 0; i < productsList.size(); i++) {
            Products prod = productsList.get(i);

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

//        setTableAlign(table, ParagraphAlignment.CENTER);


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


    public void setTableAlign(XWPFTable table, ParagraphAlignment align) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTJc jc = (tblPr.isSetJc() ? tblPr.getJc() : tblPr.addNewJc());
        STJc.Enum en = STJc.Enum.forInt(align.getValue());
        jc.setVal(en);
    }

}
