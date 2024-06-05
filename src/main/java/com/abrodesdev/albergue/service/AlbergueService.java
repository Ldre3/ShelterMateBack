package com.abrodesdev.albergue.service;


import com.abrodesdev.albergue.model.Adoptante;
import com.abrodesdev.albergue.model.Albergue;
import com.abrodesdev.albergue.model.Datos;
import com.abrodesdev.albergue.model.Usuario;
import com.abrodesdev.albergue.repository.AlbergueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AlbergueService {
    @Autowired
    private AlbergueRepository albergueRepository;

    public Albergue save(Albergue albergue) {
        System.out.println(albergueRepository.findByLocalidadIgnoreCase(albergue.getLocalidad()));
        // Si ya existe un albergue en la misma localidad, no se puede crear otro
        if (!albergueRepository.findByLocalidadIgnoreCase(albergue.getLocalidad()).isEmpty()) return null;
        return albergueRepository.save(albergue);
    }

    public List<Albergue> findByVoluntarioId(Long id) {
        return albergueRepository.findByUsuariosVoluntarios_Id(id);
    }

    // Devuelve los albergues en los que el voluntario no está
    public List<Albergue> findByVoluntarioIdNotInAlbergue(Long id) {
        return albergueRepository.findAll()
                .stream()
                .filter(albergue -> albergue.getUsuariosVoluntarios().stream().noneMatch(voluntario -> voluntario.getId()==id))
                .toList();
    }

    // Devuelve los datos de los adoptantes y voluntarios de un albergue no repetidos
    public List<Datos> findDatos(Long id) {
        Albergue albergue = albergueRepository.findById(id).orElseThrow(() -> new RuntimeException("Albergue no encontrado"));

        return Stream.concat(albergue.getAdoptantes().stream()
                .map(Adoptante::getDatos), albergue.getUsuariosVoluntarios().stream().map(Usuario::getDatos))
                .distinct()
                .toList();
    }




}
