package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.ProductsRepository;
import com.psu.kurs.demo.entity.Basket;
import com.psu.kurs.demo.entity.Products;
import com.psu.kurs.demo.entity.Requests;
import com.psu.kurs.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReduceGameService {

//    @Autowired
//    public ProductsRepository productsRepository0;


    public List<Products> changeNumbOfGame(Basket basket, ProductsRepository productsRepository, boolean increase) {

        List<Long> idGames = new ArrayList<>();

//        System.out.println("this: "+this.productsRepository.findAll().size());
        System.out.println("param: " + productsRepository.findAll().size());

        System.out.println("list: " + productsRepository.findAll());

        for (Requests requests : basket.getRequestsList()) {
            idGames.add(requests.getProducts().getId());
        }

        List<Products> productsList = new ArrayList<>();

        for (Long id : idGames) {
            Products product = productsRepository.getOne(id);
            if (increase) {
                product.setQuantity(product.getQuantity() + 1);
            } else {
                product.setQuantity(product.getQuantity() - 1);
            }
            productsList.add(product);
//            this.productsRepository.save(product);
        }
        return productsList;

    }

    public List<Products> increaseNumbOfGame(List<Products> products, ProductsRepository productsRepository) {

        List<Long> idGames = new ArrayList<>();

        System.out.println("param: " + productsRepository.findAll().size());
        System.out.println("list: " + productsRepository.findAll());

        for (Products product : products) {
            idGames.add(product.getId());
        }

        List<Products> productsList = new ArrayList<>();

        for (Long id : idGames) {
            Products product = productsRepository.getOne(id);
            product.setQuantity(product.getQuantity() + 1);
            productsList.add(product);
        }
        return productsList;
    }

}
