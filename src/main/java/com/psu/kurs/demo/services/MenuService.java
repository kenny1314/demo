package com.psu.kurs.demo.services;

import java.util.List;

import com.psu.kurs.demo.dao.GenresRepository;
import com.psu.kurs.demo.dao.PlatformsRepository;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.entity.Genres;
import com.psu.kurs.demo.entity.Platforms;
import com.psu.kurs.demo.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class MenuService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;

    public Model getMenuItems(Model model) {

        List<Platforms> platformsList;
        List<Products> productsList;
        List<Genres> genresList;

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);

            productsList = productsRepository.findAll();
            model.addAttribute("productsList", productsList);

            genresList = genresRepository.findAll();
            model.addAttribute("genresList", genresList);

        } catch (Exception ex) {

        }

        return model;
    }

}
