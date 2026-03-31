package com.mercasuper.repository;

import com.mercasuper.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 //Uso JpaRepository, para poder tener los métodos como findAll(), save() y deleteById(). 
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Recordatorio: poner o añadir consultas personalizadas en el futuro
}