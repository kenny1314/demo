package com.psu.kurs.demo.services;

import com.psu.kurs.demo.entity.Platforms;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFileToClass {

    public static List<Platforms> getListFromFile() throws IOException {
        FileReader fr = new FileReader("platform.txt");
        Scanner scanner = new Scanner(fr);

        List<Platforms> platformsList = new ArrayList<>();

        int inx = 0;
        while (scanner.hasNextLine()) {
            Platforms tempPlatform=new Platforms();

            tempPlatform.setName(scanner.nextLine());
            tempPlatform.setManufacturer(scanner.nextLine());
            tempPlatform.setGeneration(scanner.nextLine());
            tempPlatform.setRelaseDate(scanner.nextLine());
            tempPlatform.setPiecesSold(scanner.nextLine());
            tempPlatform.setCpu(scanner.nextLine());
            tempPlatform.setDescription(scanner.nextLine());
            tempPlatform.setStory(scanner.nextLine());

            platformsList.add(tempPlatform);
            inx++;
        }
        System.out.println("read file to class");

        System.out.println("size platfomslist:"+platformsList.size());
        for (Platforms pl: platformsList){
            System.out.println(pl.getName());
        }
        System.out.println("--------------------------");

        fr.close();

        return platformsList;
    }


}
