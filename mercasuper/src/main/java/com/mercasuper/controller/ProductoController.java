package com.mercasuper.controller;

import com.mercasuper.model.Producto;
import com.mercasuper.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable; // ¡Faltaba esta!
import java.util.List;

// Aqui creare el control para los productos de mercasuper para listar, crear, editar y eliminar.
// dato tecnico: Los datos de los productos invertienen a través de Spring Data JPA con Hibernate

@Controller
public class ProductoController {
//interviene la inyeccion del repositorio de los productos con la bd
    @Autowired
    private ProductoRepository productoRepository;

    // Lista de productos para ver los que se encuentran creados en la pagina
    @GetMapping("/productos")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos"; 
    }
    // Formulario para crear, actualizar o eliminar productos, esto usando el formulario de regirtro form-producto.html
    
    
    // Poder mostrar el formulario de creación vacio
    @GetMapping("/productos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "form-producto";
    }
    //eliminar producto por el id
    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
    productoRepository.deleteById(id);
    return "redirect:/productos";
}
    //editar un prodcuto ya creado
    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
    Producto producto = productoRepository.findById(id).orElse(null);
    model.addAttribute("producto", producto);
    return "form-producto"; 
}

    // Guardar el producto en MySQL
    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute("producto") Producto producto) {
        productoRepository.save(producto); // Spring Data JPA guarda el producto en la tabla
        return "redirect:/productos"; // Al terminar, nos manda de vuelta a la tabla
    }
}