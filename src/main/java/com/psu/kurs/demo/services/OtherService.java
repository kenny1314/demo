package com.psu.kurs.demo.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class OtherService {

    public String getCurrentUrl(HttpServletRequest request) {
        String str = "";
        str = request.getRequestURL().toString();
        return str.substring(22);
    }

}
