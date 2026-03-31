package com.mercasuper.controller;

import com.mercasuper.model.Usuario;
import com.mercasuper.repository.ProductoRepository; // // Para ver los productos
import com.mercasuper.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //para enviar datos al HTML
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository; //Repositorio de productos

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //Página principal
    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }

    //Página de Registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    //Procesador de datos
    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        try {
            //Encriptamos la clave
            String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);

            //usuario normal a todos los registrados
            usuario.setRol("ROLE_USER");

            //guardar en MySQL
            usuarioRepository.save(usuario);

            System.out.println("✅ Usuario guardado exitosamente: " + usuario.getCorreo());
            return "redirect:/?registrado=true";

        } catch (Exception e) {
            //ERROR EN BASE DE DATOS
            System.out.println("ERROR AL GUARDAR: " + e.getMessage());
            return "redirect:/registro?error=true";
        }
    }

    //decide a dónde enviar al usuario
    @GetMapping("/verificar-rol")
    public String verificarRol(Authentication auth) {
        //Revisa si el rol
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            //Al administrador lo manda a la gestión de productos
            return "redirect:/productos";
        } else {
            //Al usuario normal lo manda a la tienda de compras
            return "redirect:/tienda";
        }
    }

    //Muestra la página de tienda a clientes con los productos de la DB
    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        //Buscamos todos los productos en MySQL y los enviamos al HTML con el nombre "productos"
        model.addAttribute("productos", productoRepository.findAll());
        return "tienda"; 
    }
}