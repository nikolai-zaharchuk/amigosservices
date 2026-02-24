package com.amigos.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${app.page.size}")
    private Integer size;


    @RequestMapping("/")
    public String index() {

        System.out.println(size);

        return "index.html";
    }
}
