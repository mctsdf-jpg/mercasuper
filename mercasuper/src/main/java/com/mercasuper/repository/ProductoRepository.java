package com.mercasuper.repository;

import com.mercasuper.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para operaciones de base de datos sobre la entidad Producto.
 * Gracias a JpaRepository, tenemos métodos como findAll(), save() y deleteById().
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Aquí podrías añadir consultas personalizadas en el futuro
}