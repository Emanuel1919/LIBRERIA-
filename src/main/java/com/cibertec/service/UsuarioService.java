package com.cibertec.service;

import com.cibertec.model.Usuario;
import com.cibertec.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
	
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void guardarUsuario(Usuario usuario) {
        // Lógica de protección de contraseña
        if (usuario.getIdUsuario() > 0) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
            if (usuarioExistente != null) {
                if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                    usuario.setPassword(usuarioExistente.getPassword());
                }
            }
        }
        usuarioRepository.save(usuario);
    }

    // Uso de int coincidiendo con tu modelo
    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarUsuario(int id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
    
    public Usuario validarUsuario(String email, String password) {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}