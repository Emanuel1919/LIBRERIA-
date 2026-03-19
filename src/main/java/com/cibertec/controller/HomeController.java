package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cibertec.service.LibroService;

@Controller
public class HomeController {

	@Autowired
    private LibroService libroService;

    @GetMapping("/")
    public String index(Model model) {
        // Obtenemos todos los libros que insertaste con sus URLs de imagen
        model.addAttribute("libros", libroService.listarTodos());
        return "index";
    }
}