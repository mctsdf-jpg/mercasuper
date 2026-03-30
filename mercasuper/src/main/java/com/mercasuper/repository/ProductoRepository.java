package com.mercasuper.repository;

import com.mercasuper.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Producto
 * Permite realizar operaciones CRUD sobre productos
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {}