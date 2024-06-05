package com.abrodesdev.albergue.service;

import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    // Metodo que busca un usuario por email y devuelve un UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByDatos_Email(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new User(usuario.getDatos().getEmail(), usuario.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("USER")));
    }
}
