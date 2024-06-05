package com.abrodesdev.albergue.service;


import com.abrodesdev.albergue.dto.UserDTO;
import com.abrodesdev.albergue.dto.UsuarioDTO;
import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.LoginResponse;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.AlbergueRepository;
import com.abrodesdev.albergue.repository.UsuarioRepository;
import com.abrodesdev.albergue.utils.Utils;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlbergueRepository albergueRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;



    private Utils utils = new Utils();

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }



    public Usuario findByEmail(String email) {
        return usuarioRepository.findByDatos_Email(email);
    }

    public Usuario findByEmailAndPassword(String email, String password) { return usuarioRepository.findByDatos_EmailAndPassword(email, password);}

    public Usuario save(UserDTO usuario) {
        // Comprobar si el email ya existe
        if (usuarioRepository.findByDatos_Email(usuario.getDatos().getEmail()) != null) {
            return null;
        }
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        Usuario usuario1 = new Usuario(usuario.getDatos(), usuario.isLicencia(), encodedPassword);
        return usuarioRepository.save(usuario1);
    }

    public LoginResponse login(String email, String password) {
        // Comprobar si el email existe
        Usuario usuarioComprobar = findByEmail(email);
        if (usuarioComprobar != null) {
            // Comprobar si la contraseña es correcta
            String encodedPassword = usuarioComprobar.getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                Usuario usuario = findByEmailAndPassword(email, encodedPassword);
                return new LoginResponse("Login correcto", true, new UsuarioDTO(usuario));
            } else {
                System.out.println("Entered Password: " + password);
                return new LoginResponse("Contraseña incorrecta", false, null);
            }
        } else {
            return new LoginResponse("El email no existe", false, null);
        }
    }



    public Usuario registrarVoluntarioEnAlbergue(Long idUsuario, Long idAlbergue, String password) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(() -> new RuntimeException("Albergue no encontrado"));
        if (!albergue.getPassword().equals(password)) return null;


        albergue.getUsuariosVoluntarios().add(usuario);
        usuario.getAlberguesVoluntariado().add(albergue);
        albergueRepository.save(albergue);
        return usuarioRepository.save(usuario);
    }

    public Usuario recordarContrasena(String email) throws MessagingException {
        Usuario usuario = usuarioRepository.findByDatos_Email(email);
        if (usuario!=null) {
            String pass = utils.sendEmail(email);
            usuario.setPassword(passwordEncoder.encode(pass));
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public Usuario changePassword(String email, String password, String newPassword) {
        Usuario usuario = usuarioRepository.findByDatos_Email(email);
        if (usuario!=null) {
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                usuario.setPassword(passwordEncoder.encode(newPassword));
                return usuarioRepository.save(usuario);
            }
        }
        return null;
    }


}
