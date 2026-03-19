package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.model.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}