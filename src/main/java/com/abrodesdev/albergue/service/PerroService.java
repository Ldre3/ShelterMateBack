package com.abrodesdev.albergue.service;


import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Perro;
import com.abrodesdev.albergue.model.Paseo;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.AlbergueRepository;
import com.abrodesdev.albergue.repository.PerroRepository;
import com.abrodesdev.albergue.repository.UsuarioRepository;
import com.abrodesdev.albergue.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;


@Service
public class PerroService {

    @Autowired
    private PerroRepository perroRepository;


    @Autowired
    private AlbergueRepository albergueRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Utils utils = new Utils();

    public Perro save(Perro perro, Long idAlbergue ,String auth) {
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(()-> new RuntimeException("Albergue no encontrado"));
        String [] credentials = utils.decodeAuth(auth);
        if (credentials == null) return null;
        String email = credentials[0];
        String password = credentials[1];
        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return null;
        }
        return perroRepository.save(perro);
    }

    public boolean deleteById(Long id, String auth) {
        Perro perro = perroRepository.findById(id).orElseThrow(()-> new RuntimeException("Perro no encontrado"));

        Albergue albergue = perro.getAlbergue();

        String [] credentials = utils.decodeAuth(auth);

        if (credentials == null) return false;

        String email = credentials[0];
        String password = credentials[1];

        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return false;
        }

        perroRepository.delete(perro);

        return true;
    }

    public Perro findById(Long id) {
        return perroRepository.findById(id).orElse(null);
    }

    public List<Perro> findAll() {
        return perroRepository.findAll();
    }

    public List<Perro> findByNombre(String nombre) {
        return perroRepository.findByNombre(nombre);
    }

    public List<Perro> findByAlbergueId(Long id) {
        return perroRepository.findByAlbergue_Id(id);
    }




    public Perro registrarPerroAlbergue (Perro perro, Long idAlbergue, String auth){
        Albergue albergue = albergueRepository.findById(idAlbergue).orElseThrow(()-> new RuntimeException("Albergue no encontrado"));
        String [] credentials = utils.decodeAuth(auth);
        if (credentials == null) return null;
        String email = credentials[0];
        String password = credentials[1];
        Usuario usuario = usuarioRepository.findByDatos_Email(email);

        if (albergue.getUsuarioGestor().getId() != usuario.getId() || !passwordEncoder.matches(password, usuario.getPassword())){
            return null;
        }

        perro.setAlbergue(albergue);
        albergue.getPerros().add(perro);
        return perroRepository.save(perro);
    }

    public Perro registrarPaseo (Long idPerro, Paseo paseo, Long idUsuario){
        Perro perro = perroRepository.findById(idPerro).orElseThrow(()-> new RuntimeException("Perro no encontrado"));
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        paseo.setUsuario(usuario);
        usuario.getPaseo().add(paseo);
        perro.getPaseos().add(paseo);
        paseo.setPerros(perro);
        usuarioRepository.save(usuario);
        return perroRepository.save(perro);
    }



}
