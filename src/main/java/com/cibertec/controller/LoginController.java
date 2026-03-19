
package com.cibertec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.cibertec.model.Usuario;
import com.cibertec.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username, 
                               @RequestParam String password, 
                               HttpSession session, 
                               Model model) {
        
        Usuario user = usuarioService.validarUsuario(username, password);

        if (user != null) {
            // DETECCIÓN DE CUENTA INACTIVA
            // Al ser Boolean, verificamos que no sea nulo y que sea falso
            if (user.getActivo() != null && !user.getActivo()) {
                model.addAttribute("cuentaInactiva", true);
                return "login";
            }

            // Si llegó aquí, los datos son correctos y la cuenta está activa
            session.setAttribute("usuarioLogueado", user);
            return "redirect:/"; 
        }

        // Si user es null, el error es de credenciales
        model.addAttribute("error", true);
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Borra todo lo de la sesión
        return "redirect:/login?logout"; // Te manda al login
    }
}