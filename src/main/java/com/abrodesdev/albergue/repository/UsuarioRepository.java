package com.abrodesdev.albergue.repository;


import com.abrodesdev.albergue.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByDatos_Email(String email);
    Usuario findByDatos_EmailAndPassword(String email, String password);
}
