package com.mercasuper.config; // Asegúrate de que el package coincida con la carpeta

import com.mercasuper.model.Usuario;
import com.mercasuper.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //busca la tabla de xampp
        Usuario usuario = usuarioRepository.findByCorreo(email);
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + email);
        }
        return User.builder()
            .username(usuario.getCorreo())
            .password(usuario.getPassword())
            //NOTA: Toma ROLE_ADMIN y lo convierte en "ADMIN" para Spring
            .authorities(usuario.getRol())
            .build();
        }
}