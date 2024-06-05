package com.abrodesdev.albergue.service;


import com.abrodesdev.albergue.model.Adoptante;
import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Perro;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.*;
import com.abrodesdev.albergue.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptanteService {
    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @Autowired
    private AlbergueRepository albergueRepository;

    @Autowired
    private PerroRepository perroRepository;

    @Autowired
    private DatosRepository datosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Utils utils = new Utils();

    public Adoptante save(Adoptante adoptante, Long idAlbergue, String auth) {
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(()->
                new RuntimeException("Albergue no encontrado"));
        // Decodificar la autenticación
        String [] credentials = utils.decodeAuth(auth);
        // Si no se puede decodificar, devolver null
        if (credentials == null) return null;
        // Si se puede decodificar, obtener el email y la contraseña
        String email = credentials[0];
        String password = credentials[1];
        // Buscar el usuario por email
        Usuario usuario = usuarioRepository.findByDatos_Email(email);
        // Si el usuario no es el gestor del albergue o la contraseña no coincide, devolver null
        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return null;
        }
        adoptante.setAlbergue(albergue);
        albergue.getAdoptantes().add(adoptante);

        return adoptanteRepository.save(adoptante);
    }

    public Adoptante adoptarPerro(Long idDatos, Long idPerro, Long idAlbergue, String auth){
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(()-> new RuntimeException("Albergue no encontrado"));
        String [] credentials = utils.decodeAuth(auth);
        if (credentials == null) return null;
        String email = credentials[0];
        String password = credentials[1];
        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return null;
        }

        Adoptante adoptante = adoptanteRepository.findByDatos_Id(idDatos);
        Perro perro = perroRepository.findById(idPerro).orElseThrow(()->
                new RuntimeException("Perro no encontrado"));
        // Si el adoptante no existe, crearlo
        if (adoptante == null) {
            adoptante = new Adoptante();
            adoptante.setDatos(datosRepository.findById(idDatos).orElseThrow(()->
                    new RuntimeException("Datos no encontrados")));
            adoptante.setPerros(new ArrayList<>(List.of(perro)));
            adoptante.setAlbergue(albergueRepository.findById(idAlbergue).orElseThrow(()->
                    new RuntimeException("Albergue no encontrado")));
            perro.setAdoptante(adoptante);
            return adoptanteRepository.save(adoptante);
        }

        adoptante.getPerros().add(perro);
        perro.setAdoptante(adoptante);

        return adoptanteRepository.save(adoptante);
    }



}
