package com.cibertec.controller;

import com.cibertec.model.Categoria;
import com.cibertec.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // 1. Listar todas las categorías (Vista de gestión)
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("listaCategorias", categoriaService.listarCategorias());
        // Ruta: admin/categorias/listar_categoria.html
        return "admin/categorias/listar_categoria"; 
    }

    // 2. Formulario para nueva categoría
    @GetMapping("/nuevo")
    public String formularioCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "admin/categorias/crear_categoria";
    }

    // 3. Guardar o Actualizar categoría
    @PostMapping("/guardar")
    public String guardarCategoria(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/categorias";
    }
    
    // 4. Formulario para editar (Si lo necesitas más adelante)
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable int id, Model model) {
        // Buscamos la categoría por ID y la mandamos al modelo
        Categoria cat = categoriaService.buscarPorId(id);
        model.addAttribute("categoria", cat);
        
        // Ruta hacia el archivo que acabamos de crear
        return "admin/categorias/editar_categoria";
    }
    
 // Eliminar con control de errores
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable int id, RedirectAttributes flash) {
        try {
            categoriaService.eliminar(id);
            flash.addFlashAttribute("success", "Categoría eliminada.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "No se puede eliminar: existen libros vinculados a esta categoría.");
        }
        return "redirect:/categorias";
    }
}