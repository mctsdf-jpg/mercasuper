package com.mercasuper.controller;

import com.mercasuper.model.Usuario;
import com.mercasuper.repository.ProductoRepository;
import com.mercasuper.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Página principalLogin
    @GetMapping("/")
    public String mostrarLogin() {
        return "index";
    }

    // Página de Registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        try {
            String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordEncriptada);
            usuario.setRol("ROLE_USER");
            usuarioRepository.save(usuario);
            return "redirect:/?registrado=true";
        } catch (Exception e) {
            return "redirect:/registro?error=true";
        }
    }

    // Trafico de usuarios
    @GetMapping("/verificar-rol")
    public String verificarRol(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return "redirect:/productos"; 
        } else {
            return "redirect:/tienda";
        }
    }

    // Trafico edición
    @GetMapping("/productos")
    public String gestionarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos";
    }

    @GetMapping("/ventas")
    public String verVentas() {
        return "carrito-react";
    }

    @GetMapping("/tienda")
    public String mostrarTienda(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "tienda"; 
    }
}