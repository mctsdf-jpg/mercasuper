package com.mercasuper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Ruta principal
    @GetMapping("/")
    public String index() {
        // Retorna index.html en src/main/resources/templates/
        return "index";
    }
}