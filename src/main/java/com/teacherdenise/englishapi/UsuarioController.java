package com.teacherdenise.englishapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // Aumenta o Streak ao fazer login
    @PostMapping("/{id}/registrar-login")
    public Usuario registrarLogin(@PathVariable Long id) {
        Usuario usuario = repository.findById(id).orElseThrow();
        usuario.incrementarStreak();
        return repository.save(usuario);
    }
}