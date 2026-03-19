package com.cibertec.service;

import com.cibertec.model.Libro;
import com.cibertec.repository.LibroRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService { // Clase directa, sin interface

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public Libro buscarPorId(int id) {
        return libroRepository.findById(id).orElse(null);
    }

    public void eliminar(int id) {
        libroRepository.deleteById(id);
    }
}
    

