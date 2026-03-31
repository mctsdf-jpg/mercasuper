package com.mercasuper.repository;

import com.mercasuper.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    //Con esto busca automáticamente en la columna correo de la tabla de xampp
    Usuario findByCorreo(String correo);
}