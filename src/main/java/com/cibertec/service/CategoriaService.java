package com.cibertec.service;

import com.cibertec.model.Categoria;
import com.cibertec.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    // NUEVO MÉTODO - Buscar por ID
    public Categoria buscarPorId(int id) {
        // .findById devuelve un Optional; .orElse(null) devuelve el objeto o null si no lo encuentra
        return categoriaRepository.findById(id).orElse(null);
    }
    
    // OPCIONAL: Método para eliminar si decides agregarlo después
    public void eliminar(int id) {
        categoriaRepository.deleteById(id);
    }
}