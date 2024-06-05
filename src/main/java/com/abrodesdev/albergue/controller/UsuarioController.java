package com.abrodesdev.albergue.controller;



import com.abrodesdev.albergue.dto.UserDTO;

import com.abrodesdev.albergue.model.LoginResponse;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.service.UsuarioService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UserDTO usuario) {
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @PostMapping("/registrarVoluntario/{idUsuario}/{idAlbergue}")
    public ResponseEntity<Object> registrarVoluntarioEnAlbergue(@PathVariable Long idUsuario, @PathVariable Long idAlbergue, @RequestBody String pass) {
        try {
            Usuario usuario = usuarioService.registrarVoluntarioEnAlbergue(idUsuario, idAlbergue, pass);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> loginData)
    {
        LoginResponse loginResponse = usuarioService.login(loginData.get("email"), loginData.get("password"));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/recuperarContrasena")
    public ResponseEntity<Usuario> recuperarContrasena(@RequestBody String email) {
        try {
            return ResponseEntity.ok(usuarioService.recordarContrasena(email));
        } catch (MessagingException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Usuario> changePassword(@RequestBody Map<String, String> data) {
        return ResponseEntity.ok(usuarioService.changePassword(data.get("email"), data.get("oldPass"), data.get("newPass")));
    }

}
