package com.psu.kurs.demo.services;

import java.util.List;

import com.psu.kurs.demo.dao.GenresRepository;
import com.psu.kurs.demo.dao.PlatformsRepository;
import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.dao.RequestsRepository;
import com.psu.kurs.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
public class MenuService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    PlatformsRepository platformsRepository;

    @Autowired
    GenresRepository genresRepository;

    @Autowired
    UserService userService;

    @Autowired
    RequestsRepository requestsRepository;

    @Autowired
    HttpServletRequest httpServletRequest;


    public Model getMenuItems(Model model) {

        List<Platforms> platformsList;
        List<Products> productsList;
        List<Genres> genresList;

        User user = null;
        String remoteUser = httpServletRequest.getRemoteUser();
        int basketCount=0;
        if (remoteUser != null) {
            user = userService.findByUsername(httpServletRequest.getRemoteUser());
            model.addAttribute("user", user);

            List<Requests> requestsList=requestsRepository.findAll();
            for(Requests req:requestsList){

                if(req.getBasket()!=null&&req.getBasket().getId()==user.getId()){
//                    System.out.println("в корзине что-то есть");
                    basketCount++;
                }
            }
            model.addAttribute("basketCount",basketCount);

        } else {
            model.addAttribute("user", null);
        }

        try {
            //для меню
            platformsList = platformsRepository.findAll();
            model.addAttribute("platforms", platformsList);

            productsList = productsRepository.findAll();
            model.addAttribute("productsList", productsList);

            genresList = genresRepository.findAll();
            model.addAttribute("genresList", genresList);

        } catch (Exception ex) {
        ex.printStackTrace();
        }

        return model;
    }


}
