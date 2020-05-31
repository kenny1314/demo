package com.psu.kurs.demo.controller;

import com.psu.kurs.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @GetMapping("/test")
    public String test(Model model,
                       HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws ServletException, IOException {
        request.setAttribute("name", "Tom");
        request.setAttribute("age", 34);
//        request.getRequestDispatcher("/test").forward(request, response);
//        session.setAttribute("mySessionAttribute", "someValue");

        request.setAttribute("test", "test0");
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/test");

        System.out.println(dispatcher);
//        dispatcher.forward(request, response);


        ServletContext servletContext = webApplicationContext.getServletContext();
//        servletContext.ad
//        assert servletContext != null;
        servletContext.setAttribute("ntest", "test00");

        return "test";
//        return "redirect:/test?q=Thymeleaf+Is+Great!";
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    private SpringResourceTemplateResolver templateResolver;

    @GetMapping("/home")
    public String home(Model model,
                       HttpServletRequest request, HttpServletResponse response,
                       HttpSession session) throws ServletException, IOException {


        model.addAttribute("users", userRepository.findAll());

        class Ussr {
            public String name;
            public String type;

            public Ussr(String name, String type) {
                this.name = name;
                this.type = type;
            }

        }

        List<Ussr> users = new ArrayList<Ussr>();
        for (int i = 1; i < 4; i++) {
            String name = "里斯11" + i;
            String type = "管理员" + i;
            Ussr u = new Ussr(name, type);
            users.add(u);
        }
        model.addAttribute("users", users);
        model.addAttribute("name", "jyf");

        templateResolver.setUseDecoupledLogic(true); //separete logical

        return "home";
    }
}
