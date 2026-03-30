package com.mercasuper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String listarProductos() {
        // Al NO tener @ResponseBody, Spring busca "productos.html" 
        // en src/main/resources/templates/
        return "productos"; 
    }
}