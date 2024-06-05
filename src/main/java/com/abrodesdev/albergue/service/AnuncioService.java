package com.abrodesdev.albergue.service;


import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Anuncio;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.AlbergueRepository;
import com.abrodesdev.albergue.repository.AnuncioRepository;
import com.abrodesdev.albergue.repository.UsuarioRepository;
import com.abrodesdev.albergue.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private AlbergueRepository albergueRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Utils utils = new Utils();


    public boolean deleteById(Long id,String auth) {
        Anuncio anuncio = anuncioRepository.findById(id).orElseThrow(() -> new RuntimeException("Anuncio no encontrado"));
        Albergue albergue = anuncio.getAlbergue();

        String [] credentials = utils.decodeAuth(auth);

        if (credentials == null) return false;

        String email = credentials[0];
        String password = credentials[1];

        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return false;
        }

        anuncioRepository.delete(anuncio);
        return true;
    }

    public List<Anuncio> findByAlbergueId(Long id) {
        return anuncioRepository.findByAlbergue_Id(id);
    }

    public Albergue registrarAnuncioenAlbergue (Anuncio anuncio, Long idAlbergue, String auth) {
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(() -> new RuntimeException("Albergue no encontrado"));

        String [] credentials = utils.decodeAuth(auth);

        if (credentials == null) return null;

        String email = credentials[0];
        String password = credentials[1];

        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return null;
        }

        albergue.getAncuncios().add(anuncio);
        anuncio.setAlbergue(albergue);
        return albergueRepository.save(albergue);

    }
}
