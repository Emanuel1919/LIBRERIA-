package com.cibertec.controller;

import com.cibertec.model.Libro;
import com.cibertec.service.LibroService;
import com.cibertec.service.CategoriaService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/libros") // Todas las rutas del navegador empezarán con /libros
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private CategoriaService categoriaService;

    // 1. Listar Libros: La página principal donde se verán las fotos y el catálogo
    @GetMapping
    public String listarLibros(Model model) {
        // "listaLibros" es el nombre que usaremos en el HTML (Thymeleaf) para el bucle
        model.addAttribute("listaLibros", libroService.listarTodos());
        return "admin/libros/listar_libro";
    }

    // 2. Mostrar formulario para registrar un nuevo libro
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("libro", new Libro()); // Objeto vacío para el formulario
        model.addAttribute("categorias", categoriaService.listarCategorias()); // Para el desplegable
        return "admin/libros/crear_libro";
    }

    // 3. Guardar el libro: Recibe los datos del formulario y los procesa
    @PostMapping("/guardar")
    public String guardarLibro(@ModelAttribute Libro libro, 
                               @RequestParam("imagenArchivo") MultipartFile imagenArchivo) {
        
        if (!imagenArchivo.isEmpty()) {
            // Ruta relativa para la base de datos y el navegador
            String nombreFoto = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
            String rutaRelativa = "/fotos/" + nombreFoto;
            
            // Ruta absoluta para guardar el archivo físicamente en el disco
            // Importante: Asegúrate de que la carpeta 'src/main/resources/static/fotos' exista
            Path directorioImagenes = Paths.get("src//main//resources//static//fotos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytes = imagenArchivo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreFoto);
                Files.write(rutaCompleta, bytes);
                
                // Guardamos la ruta en el objeto libro
                libro.setImagenUrl(rutaRelativa);
                
            } catch (IOException e) {
                System.out.println("Error al subir la imagen: " + e.getMessage());
            }
        }
        
        libroService.guardar(libro);
        return "redirect:/libros";
    }

    // 4. Eliminar libro: Recibe el ID por la URL
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(@PathVariable int id) {
        libroService.eliminar(id);
        return "redirect:/";
    } 
    
 // 5. Eliminar libro: Recibe el ID por la URL
    @GetMapping("/eliminar1/{id}")
    public String eliminarLibroLista(@PathVariable int id) {
        libroService.eliminar(id);
        return "redirect:/libros";
    } 
    
    
    
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable int id, 
                                   @RequestParam(value = "source", defaultValue = "listado") String source, 
                                   Model model) {
        Libro libro = libroService.buscarPorId(id); 
        model.addAttribute("libro", libro);
        model.addAttribute("categorias", categoriaService.listarCategorias());
        
        // Pasamos el origen al modelo para que el HTML lo guarde
        model.addAttribute("source", source);
        
        return "admin/libros/editar_libro"; 
    }
    @PostMapping("/actualizar")
    public String actualizarLibro(@ModelAttribute Libro libro, 
                                   @RequestParam("imagenArchivo") MultipartFile imagenArchivo,
                                   @RequestParam("source") String source) {
        
        // 1. Buscamos el libro actual en la BD para no perder la imagen si no se sube una nueva
        Libro libroExistente = libroService.buscarPorId(libro.getIdLibro());

        if (!imagenArchivo.isEmpty()) {
            // 2. Si hay una nueva imagen, la procesamos
            String nombreFoto = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
            Path directorioImagenes = Paths.get("src//main//resources//static//fotos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytes = imagenArchivo.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreFoto);
                Files.write(rutaCompleta, bytes);
                
                // Guardamos la nueva ruta
                libro.setImagenUrl("/fotos/" + nombreFoto);
            } catch (IOException e) {
                System.out.println("Error al actualizar imagen: " + e.getMessage());
            }
        } else {
            // 3. Si NO se subió imagen nueva, mantenemos la URL que ya tenía
            libro.setImagenUrl(libroExistente.getImagenUrl());
        }
        
        libroService.guardar(libro);
        return "index".equals(source) ? "redirect:/" : "redirect:/libros";
    }
    
    
    
    
}