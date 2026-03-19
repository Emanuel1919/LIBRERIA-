package com.cibertec.controller;

import com.cibertec.model.Usuario;
import com.cibertec.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Importante
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());
        // Ruta corregida: admin/usuario/listar_usuarios.html
        return "admin/usuarios/listar_usuario";
    }

    @GetMapping("/nuevo")
    public String formularioUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/crear_usuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") int id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id); // Asegúrate de tener este método en tu Service
        model.addAttribute("usuario", usuario);
        return "admin/usuarios/editar_usuario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") int id) {
        usuarioService.eliminarUsuario(id); // Asegúrate de tener este método
        return "redirect:/usuarios";
    }
}